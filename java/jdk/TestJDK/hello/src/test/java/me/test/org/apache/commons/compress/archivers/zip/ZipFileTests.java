package me.test.org.apache.commons.compress.archivers.zip;

import org.apache.commons.compress.archivers.examples.Expander;
import org.apache.commons.compress.archivers.zip.ZipFile;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

/**
 * ZipFileTest类的单元测试类
 * 测试unzip方法的各种场景和边界情况
 */
public class ZipFileTests {

    /**
     * 测试用例01：目标目录已存在，正常解压
     * 设计思路：验证当目标目录已存在时，unzip方法能够正常执行解压操作
     * 重要性：这是最常见的使用场景，确保基本功能正常
     */
    @Test
    public void testUnzip_WhenTargetDirectoryExists_ShouldExtractSuccessfully() throws IOException {
        // 准备：创建临时目录和zip文件
        Path tempDir = Files.createTempDirectory("test_unzip_");
        Path targetDir = tempDir.resolve("target");
        Files.createDirectories(targetDir);
        
        File zipFile = tempDir.resolve("test.zip").toFile();
        createTestZipFile(zipFile);
        
        // 执行：调用unzip方法
        ZipFileTest zipFileTest = new ZipFileTest();
        zipFileTest.unzip(zipFile, targetDir);
        
        // 验证：确认文件被成功解压
        File extractedFile = targetDir.resolve("test.txt").toFile();
        assertTrue(extractedFile.exists(), "解压后的文件应该存在");
        
        // 验证：确认文件内容正确
        String content = Files.readString(extractedFile.toPath(), StandardCharsets.UTF_8);
        assertEquals("Hello World", content, "解压后的文件内容应该正确");
        
        // 清理
        deleteDirectory(tempDir.toFile());
    }

    /**
     * 测试用例02：目标目录不存在，需要创建目录
     * 设计思路：验证当目标目录不存在时，unzip方法能够自动创建目录并解压
     * 重要性：确保方法能够处理目录不存在的情况，这是重要的边界条件
     */
    @Test
    public void testUnzip_WhenTargetDirectoryNotExists_ShouldCreateDirectoryAndExtract() throws IOException {
        // 准备：创建临时目录和zip文件，但不创建目标目录
        Path tempDir = Files.createTempDirectory("test_unzip_");
        Path targetDir = tempDir.resolve("target");
        
        File zipFile = tempDir.resolve("test.zip").toFile();
        createTestZipFile(zipFile);
        
        // 验证：目标目录不存在
        assertFalse(Files.exists(targetDir), "目标目录在测试前不应该存在");
        
        // 执行：调用unzip方法
        ZipFileTest zipFileTest = new ZipFileTest();
        zipFileTest.unzip(zipFile, targetDir);
        
        // 验证：确认目录被创建
        assertTrue(Files.exists(targetDir), "目标目录应该被创建");
        
        // 验证：确认文件被成功解压
        File extractedFile = targetDir.resolve("test.txt").toFile();
        assertTrue(extractedFile.exists(), "解压后的文件应该存在");
        
        // 清理
        deleteDirectory(tempDir.toFile());
    }

    /**
     * 测试用例03：创建目录时抛出IOException
     * 设计思路：验证当创建目录失败时，方法能够正确抛出异常
     * 重要性：确保错误处理机制正常工作，避免静默失败
     */
    @Test
    public void testUnzip_WhenCreateDirectoryFails_ShouldThrowIOException() throws IOException {
        // 准备：创建临时目录和zip文件
        Path tempDir = Files.createTempDirectory("test_unzip_");
        Path targetDir = tempDir.resolve("target");
        
        File zipFile = tempDir.resolve("test.zip").toFile();
        createTestZipFile(zipFile);
        
        // 创建一个文件而不是目录，模拟创建目录失败的情况
        Files.createFile(targetDir);
        
        // 执行和验证：调用unzip方法应该抛出IOException
        ZipFileTest zipFileTest = new ZipFileTest();
        assertThrows(IOException.class, () -> zipFileTest.unzip(zipFile, targetDir),
                "当目标路径是文件时应该抛出IOException");
        
        // 清理
        deleteDirectory(tempDir.toFile());
    }

    /**
     * 测试用例04：ZipFile构造失败
     * 设计思路：验证当ZipFile构造失败时，方法能够正确抛出异常
     * 重要性：确保对无效的zip文件有正确的错误处理
     */
    @Test
    public void testUnzip_WhenZipFileConstructionFails_ShouldThrowIOException() throws IOException {
        // 准备：创建临时目录和无效的zip文件
        Path tempDir = Files.createTempDirectory("test_unzip_");
        Path targetDir = tempDir.resolve("target");
        Files.createDirectories(targetDir);
        
        File zipFile = tempDir.resolve("invalid.zip").toFile();
        Files.write(zipFile.toPath(), "Not a valid zip file".getBytes());
        
        // 执行和验证：调用unzip方法应该抛出IOException
        ZipFileTest zipFileTest = new ZipFileTest();
        assertThrows(IOException.class, () -> zipFileTest.unzip(zipFile, targetDir),
                "当zip文件无效时应该抛出IOException");
        
        // 清理
        deleteDirectory(tempDir.toFile());
    }

    /**
     * 测试用例05：目标目录路径为深层嵌套路径
     * 设计思路：验证当目标目录为深层嵌套路径时，方法能够正确创建所有父目录
     * 重要性：确保方法能够处理复杂的目录结构
     */
    @Test
    public void testUnzip_WhenTargetDirectoryIsDeepNestedPath_ShouldCreateAllParentDirectories() throws IOException {
        // 准备：创建临时目录和深层嵌套的目标路径
        Path tempDir = Files.createTempDirectory("test_unzip_");
        Path targetDir = tempDir.resolve("level1/level2/level3/target");
        
        File zipFile = tempDir.resolve("test.zip").toFile();
        createTestZipFile(zipFile);
        
        // 验证：深层嵌套目录不存在
        assertFalse(Files.exists(targetDir), "深层嵌套目录在测试前不应该存在");
        
        // 执行：调用unzip方法
        ZipFileTest zipFileTest = new ZipFileTest();
        zipFileTest.unzip(zipFile, targetDir);
        
        // 验证：确认所有父目录都被创建
        assertTrue(Files.exists(targetDir), "深层嵌套目录应该被创建");
        
        // 验证：确认文件被成功解压
        File extractedFile = targetDir.resolve("test.txt").toFile();
        assertTrue(extractedFile.exists(), "解压后的文件应该存在");
        
        // 清理
        deleteDirectory(tempDir.toFile());
    }

    /**
     * 测试用例06：空zip文件
     * 设计思路：验证当zip文件为空时，方法能够正常处理
     * 重要性：确保方法能够处理边界情况
     */
    @Test
    public void testUnzip_WhenZipFileIsEmpty_ShouldHandleGracefully() throws IOException {
        // 准备：创建临时目录和空的zip文件（有效的zip结构，但不包含任何条目）
        Path tempDir = Files.createTempDirectory("test_unzip_");
        Path targetDir = tempDir.resolve("target");
        Files.createDirectories(targetDir);

        File zipFile = tempDir.resolve("empty.zip").toFile();
        createEmptyZipFile(zipFile);
        
        // 执行：调用unzip方法
        ZipFileTest zipFileTest = new ZipFileTest();
        zipFileTest.unzip(zipFile, targetDir);
        
        // 验证：目标目录仍然存在
        assertTrue(Files.exists(targetDir), "目标目录应该仍然存在");
        
        // 验证：没有文件被解压（因为zip是空的）
        assertEquals(0, targetDir.toFile().listFiles().length, "不应该有文件被解压");
        
        // 清理
        deleteDirectory(tempDir.toFile());
    }

    /**
     * 测试用例07：包含子目录的zip文件
     * 设计思路：验证当zip文件包含子目录时，方法能够正确解压整个目录结构
     * 重要性：确保方法能够处理复杂的zip文件结构
     */
    @Test
    public void testUnzip_WhenZipFileContainsSubdirectories_ShouldExtractAll() throws IOException {
        // 准备：创建临时目录和包含子目录的zip文件
        Path tempDir = Files.createTempDirectory("test_unzip_");
        Path targetDir = tempDir.resolve("target");
        Files.createDirectories(targetDir);
        
        File zipFile = tempDir.resolve("test.zip").toFile();
        createZipFileWithSubdirectories(zipFile);
        
        // 执行：调用unzip方法
        ZipFileTest zipFileTest = new ZipFileTest();
        zipFileTest.unzip(zipFile, targetDir);
        
        // 验证：根目录文件被解压
        File rootFile = targetDir.resolve("root.txt").toFile();
        assertTrue(rootFile.exists(), "根目录文件应该被解压");
        
        // 验证：子目录文件被解压
        File subFile = targetDir.resolve("subdir/sub.txt").toFile();
        assertTrue(subFile.exists(), "子目录文件应该被解压");
        
        // 验证：深层子目录文件被解压
        File deepFile = targetDir.resolve("subdir/deep/deep.txt").toFile();
        assertTrue(deepFile.exists(), "深层子目录文件应该被解压");
        
        // 清理
        deleteDirectory(tempDir.toFile());
    }

    /**
     * 测试用例08：验证ZipFile资源被正确关闭
     * 设计思路：验证ZipFile对象在使用后被正确关闭，避免资源泄漏
     * 重要性：确保资源管理正确，防止内存泄漏
     */
    @Test
    public void testUnzip_ShouldCloseZipFileResource() throws IOException {
        // 准备：创建临时目录和zip文件
        Path tempDir = Files.createTempDirectory("test_unzip_");
        Path targetDir = tempDir.resolve("target");
        Files.createDirectories(targetDir);
        
        File zipFile = tempDir.resolve("test.zip").toFile();
        createTestZipFile(zipFile);
        
        // 执行：调用unzip方法
        ZipFileTest zipFileTest = new ZipFileTest();
        zipFileTest.unzip(zipFile, targetDir);
        
        // 验证：尝试删除zip文件，如果资源没有正确关闭，删除可能会失败
        // 在Windows上，如果文件被占用，删除会失败
        boolean deleted = zipFile.delete();
        assertTrue(deleted, "Zip文件应该可以被删除，说明资源已被正确关闭");
        
        // 清理
        deleteDirectory(tempDir.toFile());
    }

    /**
     * 辅助方法：创建测试用的zip文件
     */
    private void createTestZipFile(File zipFile) throws IOException {
        org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream zos = null;
        try {
            zos = new org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream(zipFile);
            org.apache.commons.compress.archivers.zip.ZipArchiveEntry entry = 
                new org.apache.commons.compress.archivers.zip.ZipArchiveEntry("test.txt");
            entry.setSize("Hello World".getBytes().length);
            zos.putArchiveEntry(entry);
            zos.write("Hello World".getBytes());
            zos.closeArchiveEntry();
        } finally {
            if (zos != null) {
                zos.close();
            }
        }
    }

    /**
     * 辅助方法：创建包含子目录的测试zip文件
     */
    private void createZipFileWithSubdirectories(File zipFile) throws IOException {
        org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream zos = null;
        try {
            zos = new org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream(zipFile);
            
            // 根目录文件
            {
                org.apache.commons.compress.archivers.zip.ZipArchiveEntry entry = 
                    new org.apache.commons.compress.archivers.zip.ZipArchiveEntry("root.txt");
                entry.setSize("Root content".getBytes().length);
                zos.putArchiveEntry(entry);
                zos.write("Root content".getBytes());
                zos.closeArchiveEntry();
            }
            
            // 子目录文件
            {
                org.apache.commons.compress.archivers.zip.ZipArchiveEntry entry = 
                    new org.apache.commons.compress.archivers.zip.ZipArchiveEntry("subdir/sub.txt");
                entry.setSize("Sub content".getBytes().length);
                zos.putArchiveEntry(entry);
                zos.write("Sub content".getBytes());
                zos.closeArchiveEntry();
            }
            
            // 深层子目录文件
            {
                org.apache.commons.compress.archivers.zip.ZipArchiveEntry entry = 
                    new org.apache.commons.compress.archivers.zip.ZipArchiveEntry("subdir/deep/deep.txt");
                entry.setSize("Deep content".getBytes().length);
                zos.putArchiveEntry(entry);
                zos.write("Deep content".getBytes());
                zos.closeArchiveEntry();
            }
        } finally {
            if (zos != null) {
                zos.close();
            }
        }
    }

    /**
     * 辅助方法：创建空的zip文件（有效的zip结构，但不包含任何条目）
     */
    private void createEmptyZipFile(File zipFile) throws IOException {
        org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream zos = null;
        try {
            zos = new org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream(zipFile);
            // 不添加任何条目，直接关闭，创建一个有效的空zip文件
        } finally {
            if (zos != null) {
                zos.close();
            }
        }
    }

    /**
     * 辅助方法：删除目录及其所有内容
     */
    private void deleteDirectory(File directory) throws IOException {
        if (directory.exists()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        deleteDirectory(file);
                    } else {
                        file.delete();
                    }
                }
            }
            directory.delete();
        }
    }
}
