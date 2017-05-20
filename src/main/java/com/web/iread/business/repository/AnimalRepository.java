package com.web.iread.business.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.web.iread.business.entity.Animal;


public interface AnimalRepository extends PagingAndSortingRepository<Animal, Long>,
JpaSpecificationExecutor<Animal>{
	public Animal findByIdAndStatusNot(Long id,Integer status);
	
//	@Query("from Animal")
//	public Animal gesdfjk();
	
	/*@Query(value="seelct 8 frm tb_xa_ wehre a.id=?1 and ,=?2",nativeQuery=true)
	public Animal findID(long id,long tid);
	
	@Query("select new DescribeVo(a.name,b.name,a.id) from a,b where a.id=b.aid")
	public Animal findIDs(long id,long tid);
	*/
		
}
