package com.github.fastdfs.core;

import com.github.fastdfs.core.constant.FastDFSConsts;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Rambo Yang
 */
public class FileMetadata {

    private final Map<String, String> attributes;

    public FileMetadata(Builder builder) {
        this.attributes = builder.attributes;
    }

    /**
     *
     * @return
     */
    public Map<String, String> getAttributes() {
        return attributes;
    }

    public byte[] toBytes() {
        return toBytes(FastDFSConsts.UTF_8);
    }

    public byte[] toBytes(Charset charset) {
        if (attributes.isEmpty()) {
            return new byte[0];
        }

        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> entry : attributes.entrySet()) {
            if (!first) {
                sb.append(FastDFSConsts.FDFS_RECORD_SEPERATOR);
            }

            sb.append(entry.getKey());
            sb.append(FastDFSConsts.FDFS_FIELD_SEPERATOR);
            sb.append(entry.getValue());
            first = false;
        }
        return sb.toString().getBytes();
    }


    /**
     *
     */
    public static class Builder {
        private Map<String, String> attributes = new HashMap<>();

        public Builder() {}

        /**
         *
         * @param name
         * @param value
         * @return
         */
        public Builder put(String name, String value) {
            this.attributes.put(name, value);
            return this;
        }

        /**
         *
         * @param attributes
         * @return
         */
        public Builder putAll(Map<String, String> attributes) {
            this.attributes.putAll(attributes);
            return this;
        }

        /**
         *
         * @return
         */
        public FileMetadata build() {
            return new FileMetadata(this);
        }
    }
}
