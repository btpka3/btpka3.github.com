#!/usr/bin/env zsh
# 统一运行脚本：保留各依赖 jar 原貌（Truffle Multi-Release 需要），用显式 classpath。
# 用法：
#   ./run.sh jvm      [extra Main args...]   # HotSpot JIT 基准
#   ./run.sh agent    [extra Main args...]   # native-image agent 采样
#   ./run.sh startup  [extra Main args...]   # 冷启动测量
#   ./run.sh native   [extra Main args...]   # 运行已构建的原生可执行文件
set -e
cd "$(dirname "$0")"

CP="target/classes:target/lib/*"

cmd="$1"; shift || true

case "$cmd" in
  jvm)
    exec java -cp "$CP" me.btpka3.nativeexpr.Main --bench --mode jvm "$@"
    ;;
  agent)
    exec java -agentlib:native-image-agent=config-output-dir=src/main/resources/META-INF/native-image \
         -cp "$CP" me.btpka3.nativeexpr.Main --agent "$@"
    ;;
  startup)
    exec java -cp "$CP" me.btpka3.nativeexpr.Main --startup "$@"
    ;;
  native)
    exec ./target/expr-bench "$@"
    ;;
  native-startup)
    # 原生可执行文件冷启动：用 time 测整进程墙钟耗时
    time ./target/expr-bench --startup "$@"
    ;;
  *)
    echo "usage: ./run.sh {jvm|agent|startup|native} [args...]"; exit 1
    ;;
esac
