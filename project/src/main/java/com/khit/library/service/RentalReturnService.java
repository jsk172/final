package com.khit.library.service;

import com.khit.library.dto.BookDTO;
import com.khit.library.dto.RentalReturnDTO;
import com.khit.library.entity.RentalReturn;
import com.khit.library.repository.RentalReturnRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RentalReturnService {
    private final RentalReturnRepository rentalReturnRepository;

    //대출리스트
    public List<RentalReturnDTO> findAll() {
        List<RentalReturn> rentalReturnList = rentalReturnRepository.findAll();
        List<RentalReturnDTO> rentalReturnDTOList = new ArrayList<>();

        for(RentalReturn rentalReturn : rentalReturnList){
            RentalReturnDTO rentalReturnDTO = RentalReturnDTO.toSaveDTO(rentalReturn);
            rentalReturnDTOList.add(rentalReturnDTO);
        }
        return rentalReturnDTOList;
    }

    //도서 대출
    public void save(RentalReturnDTO rentalReturnDTO) {
        RentalReturn rentalReturn = RentalReturn.toSaveEntity(rentalReturnDTO);
        rentalReturnRepository.save(rentalReturn);
    }

    //도서반납
    public void update(RentalReturnDTO rentalReturnDTO) {
        RentalReturn rentalReturn = RentalReturn.toUpdateEntity(rentalReturnDTO);
        rentalReturnRepository.save(rentalReturn);
    }
    public RentalReturnDTO findByRentalId(Long rentalId) {
        //        Optional<RentalReturn> rentalReturn = rentalReturnRepository.findByRentalId(rentalId);
        RentalReturn rentalReturn = rentalReturnRepository.findByRentalId(rentalId);
        RentalReturnDTO rentalReturnDTO = RentalReturnDTO.toSaveDTO(rentalReturn);
        return rentalReturnDTO;
    }

    public List<RentalReturnDTO> findByMemberMid(String mid) {
        List<RentalReturn> rentalReturnList = rentalReturnRepository.findByMemberMid(mid);
        List<RentalReturnDTO> rentalReturnDTOList = new ArrayList<>();

        for(RentalReturn rentalReturn : rentalReturnList){
            RentalReturnDTO rentalReturnDTO = RentalReturnDTO.toSaveDTO(rentalReturn);
            rentalReturnDTOList.add(rentalReturnDTO);
        }
        return rentalReturnDTOList;
    }

    public int count(Long memberId){
        return rentalReturnRepository.rentalCount(memberId);
    }

    public List<Integer> rentalAble(){
        return rentalReturnRepository.rentalAble();
    }


    /*public List<BookDTO> findOrderByRentalCount() {
        return rentalReturnRepository.findOrderByRentalCount();
    }*/
}
