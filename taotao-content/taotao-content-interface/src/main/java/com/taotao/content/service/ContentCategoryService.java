package com.taotao.content.service;

import java.util.List;

import com.taotao.common.pojo.EUTreeNode;
import com.taotao.common.pojo.TaotaoResult;

public interface ContentCategoryService {
	//通过节点的id查询该节点的自己点列表
	public List<EUTreeNode> getContentCategoryList(Long parentId);
	
	//创建节点
	/**
	 * 需要父节点的id和节点的名称
	 * @param paretId
	 * @param name
	 * @return
	 */
	TaotaoResult createContentCategory(Long parentId,String name);
	
	/**
	 * 右键节点rename
	 * @param nodeId
	 * @param name
	 * @return
	 */
	TaotaoResult updateContentCategory(Long nodeId,String name);
	
	/**
	 * 删除分类节点
	 * @param nodeId
	 * @return
	 */
	TaotaoResult deleteContentCategory(Long nodeId);
}
