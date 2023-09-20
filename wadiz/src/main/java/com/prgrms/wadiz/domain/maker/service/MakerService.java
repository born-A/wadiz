package com.prgrms.wadiz.domain.maker.service;

import com.prgrms.wadiz.domain.maker.dto.MakerServiceDTO;
import com.prgrms.wadiz.domain.maker.dto.request.MakerCreateRequestDTO;
import com.prgrms.wadiz.domain.maker.dto.request.MakerUpdateRequestDTO;
import com.prgrms.wadiz.domain.maker.dto.response.MakerResponseDTO;
import com.prgrms.wadiz.domain.maker.entity.Maker;
import com.prgrms.wadiz.domain.maker.respository.MakerRepository;
import com.prgrms.wadiz.global.util.exception.BaseException;
import com.prgrms.wadiz.global.util.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MakerService {

    private final MakerRepository makerRepository;

    @Transactional
    public Long signUpMaker(MakerCreateRequestDTO dto) {
        checkDuplicateName(dto.makerName());
        checkDuplicateEmail(dto.makerEmail());

        Maker maker = Maker.builder()
                .makerName(dto.makerName())
                .makerBrand(dto.makerBrand())
                .makerEmail(dto.makerEmail())
                .build();

        Maker savedMaker = makerRepository.save(maker);

        return savedMaker.getMakerId();
    }

    @Transactional(readOnly = true)
    public MakerResponseDTO getMaker(Long makerId) {
        Maker maker = makerRepository.findById(makerId)
                .orElseThrow(() -> {
                    log.warn("Maker {} is not found", makerId);

                    return new BaseException(ErrorCode.MAKER_NOT_FOUND);
                });

        return MakerResponseDTO.from(maker);
    }

    @Transactional(readOnly = true) //TODO: 둘을 하나로 처리해도 될듯
    public MakerServiceDTO getMakerDTO(Long makerId) {
        Maker retrivedMaker = makerRepository.findById(makerId)
                .orElseThrow(() -> {
                    log.warn("Maker {} is not found", makerId);

                    return new BaseException(ErrorCode.MAKER_NOT_FOUND);
                });

        return MakerServiceDTO.from(retrivedMaker);
    }

    @Transactional
    public MakerResponseDTO updateMaker(
            Long makerId,
            MakerUpdateRequestDTO dto
    ) {
        checkDuplicateName(dto.makerName());
        checkDuplicateEmail(dto.makerEmail());

        Maker maker = makerRepository.findById(makerId)
                .orElseThrow(() -> {
                    log.warn("Maker {} is not found", makerId);

                    return new BaseException(ErrorCode.MAKER_NOT_FOUND);
                });

        maker.updateMaker(
                dto.makerName(),
                dto.makerEmail(),
                dto.makerBrand()
        );

        return MakerResponseDTO.of(
                maker.getMakerName(),
                maker.getMakerBrand(),
                maker.getMakerEmail()
        );
    }

    @Transactional
    public void deleteMaker(Long makerId) {
        Maker maker = makerRepository.findById(makerId)
                .orElseThrow(() -> {
                    log.warn("Maker {} is not found", makerId);

                    return new BaseException(ErrorCode.MAKER_NOT_FOUND);
                });

        maker.unregisteredMaker();
    }


    public void checkDuplicateEmail(String email) { //TODO : 로그
        if(makerRepository.existsByMakerEmail(email)){

            throw new BaseException((ErrorCode.DUPLICATED_EMAIL));
        }
    }

    public void checkDuplicateName(String name) {
        if(makerRepository.existsByMakerName(name)){

            throw new BaseException((ErrorCode.DUPLICATED_NAME));
        }
    }

}