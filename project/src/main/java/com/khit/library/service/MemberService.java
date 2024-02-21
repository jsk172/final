package com.khit.library.service;

import com.khit.library.config.SecurityUser;
import com.khit.library.dto.MemberDTO;
import com.khit.library.entity.Member;
import com.khit.library.entity.Role;
import com.khit.library.exception.FinalException;
import com.khit.library.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder pwEncoder;
    private final EmailService emailService;

    //회원가입
    public void save(MemberDTO memberDTO) {
        String encPw = pwEncoder.encode(memberDTO.getPassword()); //비밀번호 암호화
        memberDTO.setPassword(encPw);
        memberDTO.setRole(Role.Member); //권한설정
        Member member = Member.toSaveEntity(memberDTO);
        memberRepository.save(member);
    }
    public Member login(Member member){
        Optional<Member> findMember = memberRepository.findByMid(member.getMid());
        if(findMember.isPresent()){
            return findMember.get();
        }else{
            return null;
        }
    }

    public MemberDTO findByMid(SecurityUser principal){
        Optional<Member> member = memberRepository.findByMid(principal.getUsername());
        MemberDTO memberDTO = MemberDTO.toSaveDTO(member.get());
        return memberDTO;
    }
    //회원목록
    public List<MemberDTO> findAll() {
        List<Member> memberList = memberRepository.findAll();
        List<MemberDTO> memberDTOList = new ArrayList<>();

        for(Member member : memberList){
            MemberDTO memberDTO = MemberDTO.toSaveDTO(member);
            memberDTOList.add(memberDTO);
        }
        return memberDTOList;
    }
    //회원 삭제
    public void deleteById(Long memberId) {
        memberRepository.deleteById(memberId);
    }
    //회원 수정
    public void update(MemberDTO memberDTO) {
        String encPw = pwEncoder.encode(memberDTO.getPassword());
        memberDTO.setPassword(encPw);
        memberDTO.setRole(Role.Member);

        Member member = Member.toUpdateEntity(memberDTO);
        memberRepository.save(member);
    }

    //회원상세보기
    public MemberDTO findById(Long memberId) {
        Optional<Member> findMember = memberRepository.findById(memberId);
        if(findMember.isPresent()){
            MemberDTO memberDTO = MemberDTO.toSaveDTO(findMember.get());
            return memberDTO;
        }else{
            throw new FinalException("페이지를 찾을 수 없습니다");
        }
    }

    public MemberDTO findByUsername(String username) {
        return null;
    }

    //아이디 중복검사
    public String checkId(String mid) {
        Optional<Member> findMember = memberRepository.findByMid(mid);
        if(mid.length() < 6){
            return "len";
        }else if(mid.length() > 15){
            return "len";
        }
        if(findMember.isEmpty()){
            return "OK";
        }else{
            return "NO";
        }
    }

    //회원 탈퇴
    public boolean withdrawal(String username, String password) {
        Optional<Member> optionalMember = memberRepository.findByMid(username);
        if(optionalMember.isPresent()){
            Member member = optionalMember.get();
            if(pwEncoder.matches(password, member.getPassword())){
                memberRepository.deleteById(member.getMemberId());
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

    //아이디 찾기
    public String findIdByEmail(String email) {
        Member member = memberRepository.findByEmail(email);
        if(member != null){
            return member.getMid();
        }else{
            return null;
        }
    }

    //이메일 아이디 일치여부 확인
    public boolean isEmailAndMidMatch(String email, String mid) {
        Member member = memberRepository.findByEmailAndMid(email, mid);
        return member != null;
    }

    //임시 비밀번호 생성, 이메일 전송
    public void sendTemporaryPassword(String email){
        String temporaryPassword = generateTemporaryPassword();
        emailService.sendTemporaryPassword(email, temporaryPassword);
    }
    public String generateTemporaryPassword(){
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        int length = 10;
        StringBuilder temporaryPassword = new StringBuilder(length);
        SecureRandom random = new SecureRandom();
        for(int i=0; i<length; i++){
            int randomIndex = random.nextInt(characters.length());
            temporaryPassword.append(characters.charAt(randomIndex));
        }

        return temporaryPassword.toString();
    }
    //임시비밀번호 저장
    public MemberDTO findByMid2(String mid) {
        Member member = memberRepository.findByMid(mid).get();
        MemberDTO memberDTO = MemberDTO.toSaveDTO(member);
        return memberDTO;
    }
    
    //페이징
	public Page<MemberDTO> paging(Pageable pageable) {
		Page<Member> memberPage = memberRepository.findAll(pageable);
		return memberPage.map(member -> MemberDTO.toSaveDTO(member));
	}
}
