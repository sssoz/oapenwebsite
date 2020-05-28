package com.trilobiet.oapen.oapenwebsite.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.trilobiet.graphqlweb.implementations.aexpgraphql2.article.ArticleImp;
import com.trilobiet.graphqlweb.implementations.aexpgraphql2.file.FileImp;
import com.trilobiet.graphqlweb.implementations.aexpgraphql2.section.SectionImp;
import com.trilobiet.graphqlweb.implementations.aexpgraphql2.service.html.HtmlArticleService;
import com.trilobiet.graphqlweb.implementations.aexpgraphql2.service.html.HtmlFileService;
import com.trilobiet.graphqlweb.implementations.aexpgraphql2.service.html.HtmlSectionService;
import com.trilobiet.graphqlweb.implementations.aexpgraphql2.service.html.HtmlSnippetService;
import com.trilobiet.graphqlweb.implementations.aexpgraphql2.service.html.HtmlTopicService;
import com.trilobiet.graphqlweb.implementations.aexpgraphql2.snippet.SnippetImp;
import com.trilobiet.graphqlweb.implementations.aexpgraphql2.topic.TopicImp;
import com.trilobiet.graphqlweb.markdown2html.Flexmark2HtmlFunction;
import com.trilobiet.graphqlweb.markdown2html.Md2HtmlArticleConverter;
import com.trilobiet.graphqlweb.markdown2html.Md2HtmlConverter;
import com.trilobiet.graphqlweb.markdown2html.Md2HtmlSectionConverter;
import com.trilobiet.graphqlweb.markdown2html.Md2HtmlSnippetConverter;
import com.trilobiet.graphqlweb.markdown2html.Md2HtmlTopicConverter;
import com.trilobiet.graphqlweb.markdown2html.StringFunction;
import com.trilobiet.oapen.oapenwebsite.repositoryclient.RepositoryService;
import com.trilobiet.oapen.oapenwebsite.repositoryclient.dspace.DSpaceRepositoryService;
import com.trilobiet.oapen.oapenwebsite.rss.RssService;
import com.trilobiet.oapen.oapenwebsite.rss.hypotheses.HypothesesRssService;

@Configuration
@ComponentScan (
	basePackages = {"com.trilobiet.oapen.oapenwebsite"},
	excludeFilters = {
			@Filter( type=FilterType.ANNOTATION, value=EnableWebMvc.class ) 
	}
)
public class RootConfiguration {
	
	@Autowired
	public Environment env;	
	
	@Bean
	public StringFunction markdownflavour() {
		return new Flexmark2HtmlFunction();
	}
	
	@Bean 
	public Md2HtmlConverter<SectionImp> sectionMdConverter() {
		return new Md2HtmlSectionConverter<SectionImp>( markdownflavour() );
	}

	@Bean 
	public HtmlSectionService<SectionImp> sectionService() {
		return new HtmlSectionService<>( env.getProperty("url_strapi"), sectionMdConverter());
	}
	

	@Bean 
	public Md2HtmlConverter<TopicImp> topicMdConverter() {
		return new Md2HtmlTopicConverter<TopicImp>( markdownflavour() );
	}

	@Bean 
	public HtmlTopicService<TopicImp> topicService() {
		return new HtmlTopicService<>( env.getProperty("url_strapi"), topicMdConverter() );
	}
	
	@Bean 
	public Md2HtmlConverter<ArticleImp> articleMdConverter() {
		return new Md2HtmlArticleConverter<ArticleImp>( markdownflavour() );
	}
	
	@Bean 
	public HtmlArticleService<ArticleImp> articleService() {
		return new HtmlArticleService<>( env.getProperty("url_strapi"), articleMdConverter() );
	}
	

	@Bean 
	public Md2HtmlConverter<SnippetImp> snippetMdConverter() {
		return new Md2HtmlSnippetConverter<SnippetImp>( markdownflavour() );
	}

	@Bean 
	public HtmlSnippetService<SnippetImp> snippetService() {
		return new HtmlSnippetService<>( env.getProperty("url_strapi"), snippetMdConverter() );
	}
	
	@Bean 
	public HtmlFileService<FileImp> fileService() {
		return new HtmlFileService<>( env.getProperty("url_strapi") );
	}

	// Test: using a parameterized dao for some article subtype
	/*
	@Bean 
	public Md2HtmlConverter<TKArticle> tkArticleMdConverter() {
		return new Md2HtmlArticleConverter<TKArticle>( markdownflavour() );
	}
	@Bean 
	public HtmlArticleService<TKArticle> tkarticleService() {
		return new HtmlArticleService<>( tkArticleDao(), tkArticleMdConverter() );
	}
	@Bean
	public GenericArticleDao<TKArticle> tkArticleDao() {
		return new GenericArticleDao<>(env.getProperty("url_strapi"), TKArticle.class, TKArticleList.class);
	}
	*/
	// test end

	
	@Bean 
	public RssService rssService() {
		return new HypothesesRssService(env.getProperty("url_feed_hypotheses"));
	}
	
	@Bean 
	public RepositoryService repositoryService() {
		return new DSpaceRepositoryService(
			env.getProperty("url_dspace_api"), 
			env.getProperty("dspace_featured_collection_id")
		);
	}
	
}

