package cn.wyb.sble.resources.queryword.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import cn.wyb.sble.resources.queryword.constant.ExceptionConstant;
import cn.wyb.sble.resources.queryword.constant.QueryWordConstant;
import cn.wyb.sble.resources.queryword.model.QueryWord;
import cn.wyb.sble.resources.queryword.service.QueryWordService;
import cn.wyb.sble.resources.queryword.util.HTTPURLUtil;
import cn.wyb.sble.resources.queryword.util.JsonUtils;
import cn.wyb.sble.resources.queryword.util.QueueWordFromSBByPool;

import com.google.gson.JsonObject;

/**
 * 查询单词信息
 * 
 * @author wangyongbing
 *
 */
@Controller
public class QueryWordController {

	private String SYS_CODE = ExceptionConstant.SYS_CODE
			+ ".QueryWordController";

	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private QueryWordService queryWordService;

	/**
	 * 存放单词的队列
	 */
	private Queue<String> wordQueue;

	/**
	 * 查询单词含义
	 * 
	 * @param request
	 * @return
	 * @throws MalformedURLException
	 */
	@RequestMapping(value = "/queryword", method = RequestMethod.GET)
	public ModelAndView queryWord(HttpServletRequest request,
			@RequestParam(value = "word", required = false) String word)
			throws MalformedURLException {
		logger.debug("start search word：{}",word);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("queryword");

		if (!StringUtils.isEmpty(word)) {
			mv.addObject("word", word);
			// 拼凑get请求的URL字串
			String getURL = QueryWordConstant.SB_URL + word;

			String resp = null;
			try {
				resp = HTTPURLUtil.doGet(getURL, null);
			} catch (IOException e) {
				logger.error(SYS_CODE + ".queryWord 扇贝网查询单个单词： {} 出现异常 ", word,
						e);
			}

			if (null == resp) {
				return mv;
			}

			QueryWord qw = null;

			// 将返回json对象进行拆分组装
			JsonObject json = JsonUtils.getMapFromJsonVO(resp);
			if (null != json) {
				qw = getQueryWord(json);
				mv.addObject("qw", qw);
			}
		}

		return mv;
	}

	/**
	 * 将返回对象封装为pojo
	 * 
	 * @param json
	 */
	private QueryWord getQueryWord(JsonObject json) {
		json = json.get("data").getAsJsonObject();
		QueryWord qw = new QueryWord();
		if (null != json) {
			qw.setCn_definition(getStringIfNull(json, "cn_definition", "defn"));
			qw.setContent(getStringIfNull(json, "content"));
			qw.setDefinition(getStringIfNull(json, "definition"));
			qw.setEn_definition(getStringIfNull(json, "en_definition", "defn"));
			// qw.setEn_definitions(json.get("en_definitions").getAsString());
			qw.setId(getStringIfNull(json, "id"));
			qw.setLearning_id(getStringIfNull(json, "learning_id"));
			qw.setPronunciation(getStringIfNull(json, "pronunciation"));
			qw.setRetention(getStringIfNull(json, "retention"));
			qw.setTarget_retention(getStringIfNull(json, "target_retention"));
			qw.setUk_audio(getStringIfNull(json, "uk_audio"));
			qw.setUs_audio(getStringIfNull(json, "us_audio"));
		}
		return qw;
	}

	/**
	 * 用key在json中查找，找不到返回null
	 * 
	 * @param json
	 * @param key
	 * @return
	 */
	private String getStringIfNull(JsonObject json, String key) {
		return null == json.get(key) ? null : json.get(key).getAsString();
	}

	/**
	 * 用key1在key在json中查找的结果中查找，找不到返回null
	 * 
	 * @param json
	 * @param key
	 * @return
	 */
	private String getStringIfNull(JsonObject json, String key, String key1) {
		return null == json.get(key) ? null : null == json.get(key)
				.getAsJsonObject() ? null : null == json.get(key)
				.getAsJsonObject().get(key1) ? null : json.get(key)
				.getAsJsonObject().get(key1).getAsString();
	}

	/**
	 * 批量查询单词含义
	 * 
	 * @param request
	 * @param words
	 *            代词以空格分割
	 * @return
	 * @throws MalformedURLException
	 */
	@RequestMapping(value = "/querywordList", method = RequestMethod.POST)
	public ModelAndView queryWordList(HttpServletRequest request,
			@RequestParam("word") String words) throws MalformedURLException {
		ModelAndView mv = new ModelAndView();
		if (!StringUtils.isEmpty(words)) {
			String[] wordList = words.split(" ");
			wordQueue = new ConcurrentLinkedQueue<String>();
			for (String s : wordList) {
				if (!StringUtils.isEmpty(s)) {
					wordQueue.add(s);
				}
			}
		}
		if (!wordQueue.isEmpty()) {
			List<QueryWord> qwl = QueueWordFromSBByPool.getInstance()
					.queryWordQueue(wordQueue);
			mv.addObject("dataMap", qwl);
		}

		mv.setViewName("queryword");
		return mv;
	}
}
