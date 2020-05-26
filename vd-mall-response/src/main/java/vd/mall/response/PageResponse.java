package vd.mall.response;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;

import java.util.List;

/**
 * Created By pengq On 2020/5/12 21:36
 */
@Data
public class PageResponse {
    private Long pageNum;
    private Long pageSize;
    private Long totalPage;
    private Long total;
    private List<?> list;

    private PageResponse() {
    }

    public Long getTotalPage() {
        long page = total / pageSize;
        return total % pageSize == 0 ? page : page + 1;
    }

    public static PageResponse build (IPage<?> pageData, boolean includeData){
        PageResponse response = new PageResponse();
        response.setPageNum(pageData.getCurrent());
        response.setPageSize(pageData.getSize());
        response.setTotal(pageData.getTotal());
        if (includeData){
            response.setList(pageData.getRecords());
        }

        return response;
    }

    public static PageResponse build (IPage<?> pageData){
        return build(pageData,true);
    }
}
