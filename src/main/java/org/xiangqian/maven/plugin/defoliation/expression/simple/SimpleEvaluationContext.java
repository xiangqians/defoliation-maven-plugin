package org.xiangqian.maven.plugin.defoliation.expression.simple;

import org.apache.commons.io.IOUtils;
import org.xiangqian.maven.plugin.defoliation.expression.EvaluationContext;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;

/**
 * @author xiangqian
 * @date 20:57:51 2022/04/20
 */
public class SimpleEvaluationContext implements EvaluationContext {

    private File file;
    private Map<String, Object> dataMap;

    public SimpleEvaluationContext(File file) throws FileNotFoundException {
        Yaml yaml = new Yaml();
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
            dataMap = yaml.load(inputStream);
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
    }

    @Override
    public Object lookupVariable(String name) {
        return dataMap.get(name);
    }

    @Override
    public void setVariable(String name, Object value) {
        // 计算如果存在则覆盖
        dataMap.computeIfPresent(name, (k, v) -> value);
    }

}
