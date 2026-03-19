package com.github.halab4dev;

import com.github.halab4dev.grpc.*;
import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@GrpcService
public class DomainDemoGrpcService extends DemoServiceGrpc.DemoServiceImplBase {

    public static final Map<String, Post> POSTS = new ConcurrentHashMap<>();

    @Override
    public void getData(GetDataRequest request, StreamObserver<GetDataResponse> responseObserver) {
        int sizeBytes = request.getSizeBytes();
        if (sizeBytes < 0) {
            sizeBytes = 0;
        }
        String payloadStr = DataPayloadGenerator.generatePayload(sizeBytes);
        GetDataResponse response = GetDataResponse.newBuilder()
                .setId(request.getId())
                .setPayload(com.google.protobuf.ByteString.copyFromUtf8(payloadStr))
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public StreamObserver<CreatePostRequest> createPosts(StreamObserver<CreatePostResponse> responseObserver) {
        POSTS.clear();

        return new StreamObserver<>() {

            @Override
            public void onNext(CreatePostRequest createPostRequest) {
                log.info("[Domain service -createPosts] - Receive create post request: {}", createPostRequest);
                Post post = new Post(String.valueOf(POSTS.size() + 1), createPostRequest.getContent());
                POSTS.put(post.getId(), post);

                CreatePostResponse response = CreatePostResponse.newBuilder().setId(post.getId()).build();
                log.info("[Domain service - createPosts] - Return post id: {}", response);
                responseObserver.onNext(response);
            }

            @Override
            public void onError(Throwable throwable) {
                log.error("[Domain service - createPosts] - Error while creating posts", throwable);
            }

            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
            }
        };
    }

    @Override
    public void listPosts(Empty request, StreamObserver<GetPostResponse> responseObserver) {
        log.info("[Domain service -listPosts] - Receive list post request: {}", request);

        POSTS.values().forEach(post -> {
            GetPostResponse postResponse = GetPostResponse.newBuilder()
                    .setId(post.getId())
                    .setContent(post.getContent())
                    .build();
            log.info("[Domain service - listPosts] - Return post: {}", postResponse);
            responseObserver.onNext(postResponse);
            sleep(1000L);
        });
        responseObserver.onCompleted();
    }

    @Override
    public void getPost(GetPostRequest request, StreamObserver<GetPostResponse> responseObserver) {
        log.info("[Domain service - getPost] - Receive get post request: {}", request);
        Post post = POSTS.get(request.getId());
        GetPostResponse postResponse = post == null
                ? GetPostResponse.getDefaultInstance()
                : GetPostResponse.newBuilder()
                .setId(post.getId())
                .setContent(post.getContent())
                .build();

        responseObserver.onNext(postResponse);
        responseObserver.onCompleted();
    }

    @Override
    public StreamObserver<DeletePostRequest> deletePosts(StreamObserver<Empty> responseObserver) {
        return new StreamObserver<>() {

            @Override
            public void onNext(DeletePostRequest deletePostRequest) {
                log.info("[Domain service - deletePosts] - Receive delete post request: {}", deletePostRequest);
                POSTS.remove(deletePostRequest.getId());
            }

            @Override
            public void onError(Throwable throwable) {
                log.error("[Domain service - deletePosts] - Error while deleting posts", throwable);
            }

            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
            }
        };
    }

    private void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

