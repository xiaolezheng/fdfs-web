package com.lxz.fdfs.support;

import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import com.google.common.collect.Lists;

/**
 * Created by xiaolezheng on 16/5/20.
 */
public class FsConstants {
    /**
     * fastdfs 默认group
     */
    public static final String DEFAULT_GROUP = "group1";

    /**
     * 缩略图默认规则
     */
    public static final String PrefixName = "_120x120";

    public static final int Default_ThumbImage_width = 120;

    public static final int Default_ThumbImage_height = 120;

    public static final List<? extends Pair> ThumbImage_Format = Lists.newArrayList(ImmutablePair.of(120, 120),
            ImmutablePair.of(160, 160), ImmutablePair.of(200, 200), ImmutablePair.of(300, 300));

}
