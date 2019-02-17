cc_binary(
    name = "hello-world",
    srcs = ["hello-world.cpp"],
    deps = ["//:hello-greet"],
)
cc_binary(
    name = "hello-dl",
    srcs = ["hello-dl.cpp"],
    linkopts = ['-ldl', '-lm', '-Wl,-rpath,bazel-bin'],
    deps = ["//:hello-greet"],
)
cc_library(
    name = "hello-greet",
    srcs = ["hello-greet.cpp"],
    hdrs = ["hello-greet.h"],
)
