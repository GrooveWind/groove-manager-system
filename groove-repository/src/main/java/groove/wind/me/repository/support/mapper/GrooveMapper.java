package groove.wind.me.repository.support.mapper;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

public interface GrooveMapper<T> extends Mapper<T>, MySqlMapper<T> {

}
