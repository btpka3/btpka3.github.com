创建 archetype
mvn archetype:generate -B
  -DarchetypeGroupId=org.apache.maven.archetypes
  -DarchetypeArtifactId=maven-archetype-archetype
  -DarchetypeVersion=1.0
  -DgroupId=me.test.maven
  -DartifactId=HelloWorld-archetype
  -Dversion=0.0.1-SNAPSHOT


使用 archetype
mvn archetype:generate -B
  -DarchetypeGroupId=me.test.maven
  -DarchetypeArtifactId=hi-archetype
  -DarchetypeVersion=0.0.1-SNAPSHOT
  -DgroupId=me.test.maven
  -DartifactId=hi
  -Dversion=0.0.1-SNAPSHOT


ArchetypeDescriptor
http://maven.apache.org/archetype/archetype-models/archetype-descriptor/archetype-descriptor.html

svn:ignore
  target
  *~
  *.ipr
  *.iws
  *.iml
  *.log
  .classpath
  .project
  .settings
