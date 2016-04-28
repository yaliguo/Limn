package base;

import com.litesuits.orm.db.annotation.Column;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.enums.AssignType;

import java.io.Serializable;

/**
 * Created by pe on 2015/12/15.
 */
public class BaseBean implements Serializable{
    /**
     *ID
     */
    @PrimaryKey(AssignType.AUTO_INCREMENT)
    @Column("_id")
    public int id;
}
