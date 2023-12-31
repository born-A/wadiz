package com.prgrms.wadiz.domain.post.controller;

import com.prgrms.wadiz.domain.post.dto.request.PostCreateRequestDTO;
import com.prgrms.wadiz.domain.post.dto.request.PostUpdateRequestDTO;
import com.prgrms.wadiz.domain.post.dto.response.PostResponseDTO;
import com.prgrms.wadiz.domain.project.service.ProjectUseCase;
import com.prgrms.wadiz.global.annotation.ApiErrorCodeExample;
import com.prgrms.wadiz.global.util.exception.ErrorCode;
import com.prgrms.wadiz.global.util.resTemplate.ResponseFactory;
import com.prgrms.wadiz.global.util.resTemplate.ResponseTemplate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(
        name = "posts",
        description = "게시글 API"
)
@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class PostController {

    private final ProjectUseCase projectUseCase;

    /**
     * Post create
     */
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "게시글 생성 성공"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "게시글 생성 실패"
            )
    })
    @ApiErrorCodeExample(
            value = ErrorCode.class,
            domain = "Board"
    )
    @Operation(
            summary = "게시글 생성",
            description = "프로젝트 id와, 게시글 요청 양식(PostCreateRequestDTO)을 이용하여 게시글을 생성합니다."
    )
    @PostMapping("/{projectId}/posts/new")
    public ResponseEntity<ResponseTemplate> createPost(
            @Parameter(description = "프로젝트 id") @PathVariable Long projectId,
            @RequestBody @Valid PostCreateRequestDTO postCreateRequestDTO
    ) {
        Long postId = projectUseCase.createPost(
                projectId,
                postCreateRequestDTO
        );

        return ResponseEntity.ok(ResponseFactory.getSingleResult(postId));
    }

    /**
     * Post 조회
     */
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "게시글 조회 성공"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "게시글 조회 실패"
            )
    })
    @ApiErrorCodeExample(
            value = ErrorCode.class,
            domain = "Board"
    )
    @Operation(
            summary = "게시글 조회",
            description = "프로젝트 id를 이용하여 게시글읗 조회합니다."
    )
    @GetMapping("/{projectId}/posts")
    public ResponseEntity<ResponseTemplate> getPost(
            @Parameter(description = "프로젝트 id") @PathVariable Long projectId
    ) {
        PostResponseDTO postResponseDTO = projectUseCase.getPost(projectId);

        return ResponseEntity.ok(ResponseFactory.getSingleResult(postResponseDTO));
    }

    /**
     * Post update
     */
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "게시글 수정 성공"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "게시글 수정 실패"
            )
    })
    @ApiErrorCodeExample(
            value = ErrorCode.class,
            domain = "Board"
    )
    @Operation(
            summary = "게시글 수정",
            description = "프로젝트 id를 이용하여 게시글을 조회한 후, 게시글을 수정합니다."
    )
    @PutMapping("/{projectId}/posts")
    public ResponseEntity<ResponseTemplate> updatePost(
            @Parameter(description = "프로젝트 id") @PathVariable Long projectId,
            @RequestBody @Valid PostUpdateRequestDTO postUpdateRequestDTO
    ) {
        projectUseCase.updatePost(projectId, postUpdateRequestDTO);

        return ResponseEntity.ok(ResponseFactory.getSuccessResult());
    }

    /**
     * Post delete
     */
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "게시글 삭제 성공"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "게시글 삭제 실패"
            )
    })
    @ApiErrorCodeExample(
            value = ErrorCode.class,
            domain = "Board"
    )
    @Operation(
            summary = "게시글 삭제",
            description = "프로젝트 id를 이용하여 게시글을 조회한 후, 게시글을 삭제합니다."
    )
    @DeleteMapping("/{projectId}/posts")
    public ResponseEntity<ResponseTemplate> deletePost(
            @Parameter(description = "프로젝트 id") @PathVariable Long projectId
    ) {
        projectUseCase.deletePost(projectId);

        return ResponseEntity.ok(ResponseFactory.getSuccessResult());
    }
}
