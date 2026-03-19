package com.github.halab4dev;

import com.github.halab4dev.grpc.*;
import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import net.devh.boot.grpc.client.inject.GrpcClient;

import java.util.List;

@Slf4j
@Component
public class DomainServiceGrpcClient {

    @GrpcClient("domain-service")
    private DemoServiceGrpc.DemoServiceBlockingStub stub;

    @GrpcClient("domain-service")
    private DemoServiceGrpc.DemoServiceStub asyncStub;

    public DataPayload call(String id, int sizeBytes) {
        GetDataRequest request = GetDataRequest.newBuilder()
                .setId(id)
                .setSizeBytes(sizeBytes)
                .build();
        GetDataResponse response = stub.getData(request);
        String payloadStr = response.getPayload().toStringUtf8();
        return new DataPayload(response.getId(), payloadStr);
    }

    public void runTest() {

        log.info("\n============= CREATE POSTS ===============");
        createPosts();
        sleep(5000);

        log.info("\n============= LIST POSTS ===============");
        listPost();

        log.info("\n============= GET POST ===============");
        getPost();

        log.info("\n============= DELETE POSTS ===============");
        deletePosts();
    }

    private void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void createPosts() {
        StreamObserver<CreatePostResponse> responseObserver = new StreamObserver<>() {

            @Override
            public void onNext(CreatePostResponse createPostResponse) {
                log.info("[Gateway - createPosts] - create post received: {}", createPostResponse);
            }

            @Override
            public void onError(Throwable throwable) {
                log.info("[Gateway - createPosts] - create post response error", throwable);
            }

            @Override
            public void onCompleted() {
                log.info("[Gateway - createPosts] - create post completed");
            }
        };


        StreamObserver<CreatePostRequest> requestObserver = asyncStub.createPosts(responseObserver);

        List<CreatePostRequest> postRequests = List.of(
                CreatePostRequest.newBuilder().setContent("Oh").build(),
                CreatePostRequest.newBuilder().setContent("Hello").build(),
                CreatePostRequest.newBuilder().setContent("gRPC").build()
        );
        try {
            postRequests.forEach( postRequest -> {
                    requestObserver.onNext(postRequest);
                    sleep(1000L);
            });
        } catch (Exception ex) {
            log.info("[Gateway - createPosts] - create post error", ex);
        }

        // Mark the end of requests
        requestObserver.onCompleted();
    }

    private void listPost() {
        var posts = stub.listPosts(Empty.newBuilder().build());
        posts.forEachRemaining(post -> {
            log.info("[Gateway - listPost] - list post received: {}", post);
        });
    }

    private void getPost() {
        var post = stub.getPost(GetPostRequest.newBuilder().setId("1").build());
        log.info("[Gateway - getPost] - get post received: {}", post);

    }

    private void deletePosts() {

        StreamObserver<DeletePostRequest> requestObserver = asyncStub.deletePosts(new StreamObserver<>() {

            @Override
            public void onNext(Empty empty) {
                log.info("[Gateway - deletePosts] - on next response: {}", empty);
            }

            @Override
            public void onError(Throwable throwable) {

                log.info("[Gateway - deletePosts] - error while deleting posts", throwable);
            }

            @Override
            public void onCompleted() {

                log.info("[Gateway - deletePosts] - completed");
            }
        });

        List<DeletePostRequest> deletePostRequests = List.of(
                DeletePostRequest.newBuilder().setId("1").build(),
                DeletePostRequest.newBuilder().setId("2").build(),
                DeletePostRequest.newBuilder().setId("3").build()
        );
        try {
            deletePostRequests.forEach( postRequest -> {
                requestObserver.onNext(postRequest);
                sleep(1000L);
            });
        } catch (Exception ex) {
            log.info("[Gateway - deletePosts] - delete post error", ex);
        }
    }
}

