package com.khit.library.service;

import com.khit.library.dto.ReadingRoomDTO;
import com.khit.library.entity.ReadingRoom;
import com.khit.library.exception.FinalException;
import com.khit.library.repository.ReadingRoomRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ReadingRoomService {
    private final ReadingRoomRepository readingRoomRepository;

    //좌석
    public List<ReadingRoomDTO> findAll() {
        List<ReadingRoom> readingRoomList = readingRoomRepository.findAll();
        List<ReadingRoomDTO> readingRoomDTOList = new ArrayList<>();

        for(ReadingRoom readingRoom : readingRoomList){
            ReadingRoomDTO readingRoomDTO = ReadingRoomDTO.toSaveDTO(readingRoom);
            readingRoomDTOList.add(readingRoomDTO);
        }
        return readingRoomDTOList;
    }

    //좌석선택
    public void select(ReadingRoomDTO readingRoomDTO){
        ReadingRoom readingRoom = ReadingRoom.toSaveEntity(readingRoomDTO);
        readingRoomRepository.select(readingRoom.getMember().getMemberId(), readingRoom.getCheckOut(), readingRoom.getSeat());
    }
    //좌석반납
    public void checkout(ReadingRoomDTO readingRoomDTO){
        ReadingRoom readingRoom = ReadingRoom.toUpdateEntity(readingRoomDTO);
        readingRoomRepository.checkout(readingRoom.getSeat());
    }


    public ReadingRoomDTO findById(Long readingId) {
        Optional<ReadingRoom> findSeat = readingRoomRepository.findById(readingId);
        if(findSeat.isPresent()){
            ReadingRoomDTO readingRoomDTO = ReadingRoomDTO.toSaveDTO(findSeat.get());
            return readingRoomDTO;
        }else{
            throw new FinalException("좌석이 없습니다.");
        }
    }

    @Transactional
    public int seat(Long memberId){
        return readingRoomRepository.countSeatsByMemberId(memberId);
    }
}
