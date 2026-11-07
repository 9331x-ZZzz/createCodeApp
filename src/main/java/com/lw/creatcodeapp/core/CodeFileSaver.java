package com.lw.creatcodeapp.core;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.lw.creatcodeapp.ai.model.HtmlCodeResult;
import com.lw.creatcodeapp.ai.model.MultiFileCodeResult;
import com.lw.creatcodeapp.model.enums.CodeGenTypeEnum;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;


/**
 * 代码文件保存器
 */
public class CodeFileSaver {

    //文件保存的根目录
    private static final String FILE_SAVE_ROOT_DIR =
            System.getProperty("user.dir") + "/tmp/code_output";

/**
* 保存 HTML 网页代码
*
* @param htmlCodeResult
* @return
*/
    public static File saveHtmlCodeResult(HtmlCodeResult htmlCodeResult) {
        String baseDirPath = buildUniqueDir(CodeGenTypeEnum.HTML.getValue());
        writeToFile(baseDirPath, "index.html", htmlCodeResult.getHtmlCode());
        return new File(baseDirPath);
    }
/**
* 保存多文件网页代码
*
* @param result
* @return
*/
    public static File saveMultiFileCodeResult(MultiFileCodeResult result) {
        String baseDirPath = buildUniqueDir(CodeGenTypeEnum.MULTI_FILE.getValue());
        writeToFile(baseDirPath, "index.html", result.getHtmlCode());
        writeToFile(baseDirPath, "style.css", result.getCssCode());
        writeToFile(baseDirPath, "script.js", result.getJsCode());
        return new File(baseDirPath);
    }
/**
 * 构建唯一的文件目录路径
 * 该方法根据业务类型和雪花算法生成的ID创建一个唯一的目录路径
 * 目录路径格式为：FILE_SAVE_ROOT_DIR/业务类型_雪花ID
 *
 * @param bizType 业务类型，用于标识不同业务的文件存储
 * @return 返回创建好的完整目录路径
 */
    private static String buildUniqueDir(String bizType) {
    // 使用业务类型和雪花算法生成的ID格式化生成唯一目录名称
        String uniqueDirName = StrUtil.format("{}_{}", bizType, IdUtil.getSnowflakeNextIdStr());
    // 拼接完整的目录路径，使用系统文件分隔符确保跨平台兼容性
        String dirPath = FILE_SAVE_ROOT_DIR + File.separator + uniqueDirName;
    // 创建目录（如果不存在），FileUtil.mkdir会自动处理不存在的父目录
        FileUtil.mkdir(dirPath);
    // 返回创建好的目录路径
        return dirPath;
    }

/**
 * 保存单个文件的方法
 * 将指定内容写入到指定路径的文件中
 *
 * @param dirPath 文件所在的目录路径
 * @param filename 要保存的文件名
 * @param content 要写入文件的内容
 */
    //保存单个文件
    public static void writeToFile(String dirPath, String filename,String content) {
    // 组合完整的文件路径，使用系统相关的分隔符
        String filePath = dirPath + File.separator + filename;
    // 使用FileUtil工具类将内容以UTF-8编码写入指定路径的文件
        FileUtil.writeString(content, filePath, StandardCharsets.UTF_8);
    }
}
