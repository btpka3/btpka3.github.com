package me.btpka3.nativeexpr;

/**
 * 统一引擎适配接口。
 * compile: 预编译一次（度量编译耗时；对可缓存的引擎复用编译产物）
 * eval:    热路径，每事件调用一次
 */
public interface Engine extends AutoCloseable {

    /** 引擎标识，需与 Cases.exprByEngine 的 key 一致（PureJava 例外）。 */
    String key();

    /** 展示名 */
    String displayName();

    /** 该引擎是否在当前运行环境可用（例如 native 下某模式不可用时返回 false）。 */
    default boolean available() { return true; }

    /**
     * 预编译一个用例，返回引擎私有的已编译对象。
     * 若该引擎不支持此用例（语法/能力缺失），抛异常，由上层记为 UNSUPPORTED。
     */
    Object compile(LogicalCase c) throws Exception;

    /** 热路径求值。返回原始结果，由 Harness 归一化。 */
    Object eval(Object prepared, Fact fact) throws Exception;

    @Override
    default void close() {}
}
