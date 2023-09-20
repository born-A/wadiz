package com.prgrms.wadiz.domain.post.service;

import com.prgrms.wadiz.domain.post.dto.request.PostCreateRequestDTO;
import com.prgrms.wadiz.domain.post.dto.request.PostUpdateRequestDTO;
import com.prgrms.wadiz.domain.post.dto.response.PostResponseDTO;
import com.prgrms.wadiz.domain.post.entity.Post;
import com.prgrms.wadiz.domain.post.repository.PostRepository;
import com.prgrms.wadiz.domain.project.ProjectStatus;
import com.prgrms.wadiz.domain.project.dto.ProjectServiceDTO;
import com.prgrms.wadiz.domain.project.entity.Project;
import com.prgrms.wadiz.global.util.exception.BaseException;
import com.prgrms.wadiz.global.util.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    @Transactional
    public Long createPost(
            ProjectServiceDTO projectServiceDTO,
            PostCreateRequestDTO postCreateRequestDTO
    ) {
        Project project = ProjectServiceDTO.toEntity(projectServiceDTO);

        Post post = Post.builder()
                .project(project)
                .postTitle(postCreateRequestDTO.postTitle())
                .postDescription(postCreateRequestDTO.postDescription())
                .postThumbNailImage(postCreateRequestDTO.postThumbNailImage())
                .postContentImage(postCreateRequestDTO.postContentImage())
                .build();

        Post savedPost = postRepository.save(post);

        return savedPost.getPostId();
    }

    @Transactional(readOnly = true)
    public PostResponseDTO getPostByProjectId(Long projectId) {
        Post post = postRepository.findByProject_ProjectId(projectId)
                .orElseThrow(() -> {
                    log.warn("Post for Project {} is not found", projectId);

                    throw new BaseException(ErrorCode.POST_NOT_FOUND);
                });

        return PostResponseDTO.from(post);
    }

    @Transactional
    public void updatePost(
            Long projectId,
            PostUpdateRequestDTO postUpdateRequestDTO
    ) {
        Post post = postRepository.findByProject_ProjectId(projectId)
                .orElseThrow(() -> {
                    log.warn("Post for Project {} is not found", projectId);

                    throw new BaseException(ErrorCode.POST_NOT_FOUND);
                });

        if (!isProjectBeforeSetUp(post.getProject())) {
            throw new BaseException(ErrorCode.PROJECT_ACCESS_DENY);
        }

        post.updatePost(
                postUpdateRequestDTO.postTitle(),
                postUpdateRequestDTO.postDescription(),
                postUpdateRequestDTO.postThumbNailImage(),
                postUpdateRequestDTO.postContentImage()
        );
    }

    @Transactional
    public void deletePost(Long projectId) {
        Post post = postRepository.findByProject_ProjectId(projectId)
                .orElseThrow(() -> {
                    log.warn("Post for Project {} is not found", projectId);

                    throw new BaseException(ErrorCode.POST_NOT_FOUND);
                });

        if (!isProjectBeforeSetUp(post.getProject())) {
            throw new BaseException(ErrorCode.PROJECT_ACCESS_DENY);
        }

        postRepository.deleteByProject_ProjectId(projectId);
    }

    public boolean isPostExist(Long projectId) {
        return postRepository.findByProject_ProjectId(projectId).isPresent();
    }

    private boolean isProjectBeforeSetUp(Project project) {
        return project.getProjectStatus() == ProjectStatus.READY;
    }
}
