java_binary(
    name = "hello",
    srcs = ["src/main/java/com/demo/DemoRunner.java"],
    main_class = "com.demo.DemoRunner",
    deps = [":HelloTest"],
)

java_library(
    name = "HelloTest",
    srcs = ["src/main/java/com/demo/Hello.java"],
    visibility = ["//src/main/java/com/demo/child:__pkg__"],
)
