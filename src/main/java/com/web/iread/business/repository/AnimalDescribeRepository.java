package com.web.iread.business.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.web.iread.business.entity.AnimalDescribe;



public interface AnimalDescribeRepository extends PagingAndSortingRepository<AnimalDescribe, Long>,
JpaSpecificationExecutor<AnimalDescribe>{
	public AnimalDescribe findByIdAndStatusNot(Long id,Integer status);
	
	@Query(value="select new AnimalDescribe(ad.describeName,a.animalName,ad.describeColor,ad.status) from AnimalDescribe ad, Animal a where ad.tid=a.id and ad.status<>3 and a.status<>3 ")
	public List<AnimalDescribe> findListAnimalManager();
	
	/*@Query(value="select adn.describe_name,txa.animal_name,adn.describe_color,adn.`status` from tb_xa_animal_describea_name adn,tb_xa_animal txa where adn.tid = txa.id and txa.`status` <> 3 and adn.`status` <> 3",nativeQuery=true)
	public List<AnimalDescribe> findListAnimalManager();*/
	
	@Query(value="select count(*) from tb_xa_animal txa,tb_xa_animal_describea_name adn where txa.id=adn.tid",nativeQuery=true)
	public int finListAnimalPage();
	
	
	/*
	@Query(value="select adn.describe_name,txa.animal_name,adn.describe_color,adn.`status` " +
			     "from tb_xa_animal_describea_name adn,tb_xa_animal txa " +
			     "where adn.tid = txa.id and txa.`status` <> 3 and adn.`status` <> 3",
			     countQuery = "select count(*) " +
			     		"from tb_xa_animal txa,tb_xa_animal_describea_name adn " +
			     		"where txa.id=adn.tid and txa.`status`<>3 and adn.`status`<> 3"，,nativeQuery=true)
	public Page<DescribeVo> findListAnimalManager2();*/
}
