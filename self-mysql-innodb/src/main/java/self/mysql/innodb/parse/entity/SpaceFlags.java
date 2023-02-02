package self.mysql.innodb.parse.entity;

import lombok.Data;
import self.mysql.innodb.parse.BitReader;
import self.mysql.innodb.parse.PageReader;

/**
 * Space Flags(4): 表空间的一些占用存储空间比较小的属性
 *
 * @author chenzibin
 * @date 2023/2/1
 */
@Data
public class SpaceFlags {

    /**
     * POST_ANTELOPE(1bit): 表示文件格式是否大于ANTELOPE
     */
    private boolean postAntelope;

    /**
     * ZIP_SSIZE(4bit): 表示压缩页面的大小
     */
    private int zipSize;

    /**
     * ATOMIC_BLOBS(1bit): 表示是否自动把值非常长的字段放到BLOB页里
     */
    private boolean atomicBlobs;

    /**
     * PAGE_SSIZE(4bit): 页面大小
     */
    private int pageSize;

    /**
     * DATA_DIR(1bit): 	表示表空间是否是从默认的数据目录中获取的
     */
    private boolean dataDir;

    /**
     * SHARED(1bit): 是否为共享表空间
     */
    private boolean shared;

    /**
     * TEMPORARY(1bit): 是否为临时表空间
     */
    private boolean temporary;

    /**
     * ENCRYPTION(1bit): 表空间是否加密
     */
    private boolean encryption;

    /**
     * UNUSED(18bit): 没有使用到的比特位
     */
    private int unused;

    public SpaceFlags(PageReader reader) {
        BitReader bitReader = new BitReader(reader.readInt());
        this.postAntelope = bitReader.readBool();
        this.zipSize = bitReader.readInt(4);
        this.atomicBlobs = bitReader.readBool();
        this.pageSize = bitReader.readInt(4);
        this.dataDir = bitReader.readBool();
        this.shared = bitReader.readBool();
        this.temporary = bitReader.readBool();
        this.encryption = bitReader.readBool();
        this.unused = bitReader.readInt(18);
    }
}
