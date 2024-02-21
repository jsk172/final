package com.khit.library.service;

import com.khit.library.config.SecurityUser;
import com.khit.library.dto.BookDTO;
import com.khit.library.dto.MemberDTO;
import com.khit.library.dto.RentalReturnDTO;
import com.khit.library.entity.Book;
import com.khit.library.entity.RentalReturn;
import com.khit.library.exception.FinalException;
import com.khit.library.repository.BookRepository;
import com.khit.library.repository.RentalReturnRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class BookService {
    private final BookRepository bookRepository;
    private final RentalReturnRepository rentalReturnRepository;

    //책 등록
    public void save(BookDTO bookDTO, MultipartFile bookFile) throws Exception {
        if(!bookFile.isEmpty()){
            UUID uuid = UUID.randomUUID();
            String bfilename = uuid + "_" + bookFile.getOriginalFilename(); //원본파일
            String bfilepath = "C:/projectfiles/" + bfilename;

            File savedFile = new File(bfilepath);
            bookFile.transferTo(savedFile);

            bookDTO.setBfilename(bfilename);
            bookDTO.setBfilepath(bfilename);
        }
        Book book = Book.toSaveEntity(bookDTO);
        bookRepository.save(book);
    }
    
    //책 리스트
    public List<BookDTO> findAll() {
        List<Book> bookList = bookRepository.findAll();
        List<BookDTO> bookDTOList = new ArrayList<>();

        for(Book book : bookList){
            BookDTO bookDTO = BookDTO.toSaveDTO(book);
            bookDTOList.add(bookDTO);
        }
        return bookDTOList;
    }
    //상세보기
    public BookDTO findById(Long bookId) {
        Optional<Book> findBook = bookRepository.findById(bookId);
        if(findBook.isPresent()){
            BookDTO bookDTO = BookDTO.toSaveDTO(findBook.get());
            return bookDTO;
        }else{
            throw new FinalException("페이지를 찾을 수 없습니다.");
        }
    }
    //책 수정
    public void update(BookDTO bookDTO, MultipartFile bookFile) throws Exception {
        if(!bookFile.isEmpty()){
            UUID uuid = UUID.randomUUID();
            String bfilename = uuid + "_" + bookFile.getOriginalFilename(); //원본파일
            String bfilepath = "C:/projectfiles/" + bfilename;

            File savedFile = new File(bfilepath);
            bookFile.transferTo(savedFile);

            bookDTO.setBfilename(bfilename);
            bookDTO.setBfilepath(bfilename);
        }else{
            bookDTO.setBfilename(findById(bookDTO.getBookId()).getBfilename());
            bookDTO.setBfilepath(findById(bookDTO.getBookId()).getBfilepath());
        }
        Book book = Book.toUpdateEntity(bookDTO);
        bookRepository.save(book);
    }
    //책 삭제
    public void deleteById(Long bookId) {
        bookRepository.deleteById(bookId);
    }

    public void rentBook(Long bookId, MemberDTO memberDTO) {
    }
    
    //검색
    public Page<BookDTO> search(String keyword, Pageable pageable) {
        Page<Book> searchResults = bookRepository.findByBnameContainingIgnoreCaseOrAuthorContainingIgnoreCase(keyword, pageable);
        
        // Page<Book>를 Page<BookDTO>로 변환
        return searchResults.map(BookDTO::toSaveDTO);
    }


	public Page<BookDTO> paging(Pageable pageable) {
		Page<Book> bookPage = bookRepository.findAll(pageable);
		return bookPage.map(book -> BookDTO.toSaveDTO(book));
	}

}
