package com.prgrms.wadiz.domain.reward.controller;

import com.prgrms.wadiz.domain.project.ProjectStatus;
import com.prgrms.wadiz.domain.project.dto.ProjectServiceDTO;
import com.prgrms.wadiz.domain.reward.dto.request.RewardCreateRequestDTO;
import com.prgrms.wadiz.domain.reward.dto.request.RewardUpdateRequestDTO;
import com.prgrms.wadiz.domain.reward.dto.response.RewardResponseDTO;
import com.prgrms.wadiz.domain.reward.service.RewardService;
import com.prgrms.wadiz.global.util.resTemplate.ResponseFactory;
import com.prgrms.wadiz.global.util.resTemplate.ResponseTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class RewardController {

    private final RewardService rewardService;

    @PostMapping("/{projectId}/rewards")
    public ResponseEntity<ResponseTemplate> createReward(
            @PathVariable Long projectId,
            @RequestBody @Valid RewardCreateRequestDTO rewardCreateRequestDTO
    ) {
        ProjectServiceDTO projectServiceDTO = ProjectServiceDTO.builder()
                .projectId(projectId)
                .projectStatus(ProjectStatus.SET_UP)
                .build();

        rewardService.createReward(projectServiceDTO, rewardCreateRequestDTO);
        return ResponseEntity.ok(ResponseFactory.getSuccessResult());
    }

    @PutMapping("/{projectId}/rewards/{rewardId}")
    public ResponseEntity<ResponseTemplate> updateReward(
            @PathVariable Long rewardId,
            @RequestBody @Valid RewardUpdateRequestDTO dto
    ) {
        rewardService.updateReward(rewardId, dto);
        return ResponseEntity.ok(ResponseFactory.getSuccessResult());
    }

    @GetMapping("/{projectId}/rewards/{rewardId}")
    public ResponseEntity<ResponseTemplate> getReward(@PathVariable Long rewardId) {
        RewardResponseDTO rewardResponseDTO = rewardService.getReward(rewardId);
        return ResponseEntity.ok(ResponseFactory.getSingleResult(rewardResponseDTO));
    }

    @DeleteMapping("/{projectId}/rewards/{rewardId}")
    public ResponseEntity<ResponseTemplate> deleteReward(@PathVariable Long rewardId) {
        rewardService.deleteReward(rewardId);
        return ResponseEntity.ok(ResponseFactory.getSuccessResult());
    }
}
