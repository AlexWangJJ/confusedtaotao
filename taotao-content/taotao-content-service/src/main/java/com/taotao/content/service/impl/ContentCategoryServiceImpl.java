package com.taotao.content.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.builder.annotation.MapperAnnotationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.EUTreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.content.service.ContentCategoryService;
import com.taotao.mapper.TbContentCategoryMapper;
import com.taotao.pojo.TbContentCategory;
import com.taotao.pojo.TbContentCategoryExample;
import com.taotao.pojo.TbContentCategoryExample.Criteria;

@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

	@Autowired
	private TbContentCategoryMapper contentCategoryMapper;

	/**
	 * @author AlexWang
	 * 
	 */
	@Override
	public TaotaoResult createContentCategory(Long parentId, String name) {
		// 构建对象不全其他属性
		TbContentCategory contentCategory = new TbContentCategory();
		contentCategory.setCreated(new Date());
		contentCategory.setIsParent(false);
		contentCategory.setParentId(parentId);
		contentCategory.setName(name);
		contentCategory.setSortOrder(1);
		contentCategory.setUpdated(new Date());

		// 插入数据
		contentCategoryMapper.insertSelective(contentCategory);
		// 返回内容分类的id 需要逐渐返回

		// 如果父亲是叶子结点需要改变状态
		TbContentCategory category = contentCategoryMapper.selectByPrimaryKey(parentId);

		if (category.getIsParent() == false) {
			category.setIsParent(true);
			contentCategoryMapper.updateByPrimaryKeySelective(category);// 更新节点的属性为true
		}

		return TaotaoResult.ok(contentCategory);
	}

	@Override
	public TaotaoResult updateContentCategory(Long nodeId, String name) {
		// mapper中有两种更新方式一种是直接使用主键进行更新
		// 另一种是使用example进行更新
		TbContentCategory category = new TbContentCategory();
		category.setId(nodeId);
		category.setName(name);

		contentCategoryMapper.updateByPrimaryKey(category);
		return TaotaoResult.ok(category);
	}

	/**
	 * @author AlexWang
	 * @param 传入需要被删除的节点id
	 * @return
	 */
	@Override
	public TaotaoResult deleteContentCategory(Long nodeId) {
		// 从数据库中将节点拿出来
		TbContentCategory category = contentCategoryMapper.selectByPrimaryKey(nodeId);
		// 查看是否为父节点如果是直接返回
		if (category.getIsParent()) {
			return null;
		}
		// 获取父节点的子节点集合
		TbContentCategoryExample example = new TbContentCategoryExample();

		Criteria criteria = example.createCriteria();

		criteria.andParentIdEqualTo(category.getParentId());

		List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);

		// 如果父节点只有一个子节点删除之后父节点会直接变成叶节点

		if (list.size() == 1) {

			// 这就是父节点

			TbContentCategory father = list.get(0);

			// 修改之后进行存储

			father.setIsParent(false);

			// 通过主键的形式进行修改

			contentCategoryMapper.updateByPrimaryKey(father);
		}
		// 开始删除
		contentCategoryMapper.deleteByPrimaryKey(nodeId);
		return TaotaoResult.ok();
	}

	/**
	 * @author AlexWang
	 * @param 传入的父节点的值
	 * @return 返回父节点下的子节点列表
	 */
	@Override
	public List<EUTreeNode> getContentCategoryList(Long parentId) {
		// 1、取查询参数id，parentId
		// 2、根据parentId查询tb_content_category，查询子节点列表。
		TbContentCategoryExample example = new TbContentCategoryExample();
		// 设置查询条件
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		// 执行查询
		// 3、得到List<TbContentCategory>
		List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
		// 4、把列表转换成List<EasyUITreeNode>ub
		List<EUTreeNode> resultList = new ArrayList<>();
		for (TbContentCategory tbContentCategory : list) {
			EUTreeNode node = new EUTreeNode();
			node.setId(tbContentCategory.getId());
			node.setText(tbContentCategory.getName());
			node.setState(tbContentCategory.getIsParent() ? "closed" : "open");
			// 添加到列表
			resultList.add(node);
		}
		return resultList;
	}

}
