interface IMy {
    /**
     * 测试方法
     */
    default void f1() {
    }

    /**
     * 测试方法
     */
    default void f2() {
    }

    /**
     * 测试方法
     */
    default void f3() {
        System.out.println("f3");
        sf();
    }

    /**
     * 测试方法
     */
    static void sf() {
        System.out.println("f1, f2");
    }
}

/**
 * @author liming.gong
 */
class DefaultMethod implements IMy {
    @Override
    public void f3() {
        f1();
        f2();
        IMy.super.f3();
        System.out.println("override f3");
    }

    public static void main(String[] args) {
        new DefaultMethod().f3();
    }
}