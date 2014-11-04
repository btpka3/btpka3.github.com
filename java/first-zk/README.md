该工程旨在学习如何使用ZooKeeper完成分布式锁的功能。

由于ZooKeeper自身提供的API比较基础，对与专注于业务开发的人来说过于低级了。这里使用Curator的API完成。

Curator自身提供了一个示例用的jar包。位于 Curator-examples-2.6.0.jar 中，请参考 locking.LockingExample