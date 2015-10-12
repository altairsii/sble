package cn.wyb.sble.resources.queryword.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.wyb.sble.resources.queryword.constant.ExceptionConstant;
import cn.wyb.sble.resources.queryword.constant.QueryWordConstant;
import cn.wyb.sble.resources.queryword.model.QueryWord;

/**
 * 采用多线程的方式去扇贝查询单词
 * 
 * @author wangyongbing
 *
 */
public class QueueWordFromSBByPool {
	
	private String SYS_CODE  = ExceptionConstant.SYS_CODE+".QueueWordFromSBByPool";
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	private static QueueWordFromSBByPool queueWordFromSBByPool;

	private static ExecutorService pool;
	
	static {
		pool = new ThreadPoolExecutor(0, 10, 0, null,
				new SynchronousQueue<Runnable>(),
				new ThreadPoolExecutor.CallerRunsPolicy());
	}

	public static QueueWordFromSBByPool getInstance() {
		if(null == queueWordFromSBByPool){
			queueWordFromSBByPool = new QueueWordFromSBByPool();
		}
		return queueWordFromSBByPool;
	}

	public List<QueryWord> queryWordQueue(Queue<String> wordQueue) {
		if(wordQueue.isEmpty()){
			return null;
		}
		
		final List<QueryWord> queryWord = new ArrayList<QueryWord>();
		
		for(final String s : wordQueue){
			pool.execute(new Runnable() {
				public void run() {
					queryWord(s,queryWord);
				}
			});
		}
		
		return queryWord;
	}

	/**
	 * 实际查询操作
	 * 
	 * @param word
	 * @param queryWord 
	 */
	private void queryWord(String word, List<QueryWord> queryWord) {
		// 拼凑get请求的URL字串
		String getURL = QueryWordConstant.SB_URL + word;

		String resp = null;
		try {
			resp = HTTPURLUtil.doGet(getURL, null);
		} catch (IOException e) {
			logger.error(SYS_CODE + ".queryWord : 向扇贝发送请求查询单词：{} 出错",word);
		}

		QueryWord qw = JsonUtils.getPogo(resp, QueryWord.class);
		
		queryWord.add(qw);
	}
}
