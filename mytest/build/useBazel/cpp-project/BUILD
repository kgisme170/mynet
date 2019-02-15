cc_binary(
    name = "hello-world",
    srcs = ["hello-world.cc"],
    deps = ["//:hello-greet"],
)
cc_binary(
    name = "hello-dl",
    srcs = ["hello-dl.cc"],
    linkopts = ['-ldl', '-lm', '-Wl,-rpath,bazel-bin'],
    deps = ["//:hello-greet"],
)
cc_library(
    name = "hello-greet",
    srcs = ["hello-greet.cc"],
    hdrs = ["hello-greet.h"],
)
