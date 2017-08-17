package com.qsm.ad.pageprocessor

import us.codecraft.webmagic.Page
import us.codecraft.webmagic.Site
import us.codecraft.webmagic.Spider
import us.codecraft.webmagic.processor.PageProcessor
import us.codecraft.webmagic.processor.example.GithubRepoPageProcessor

/**
 * Created by TQ on 2017/8/17.
 */
class QsmADPageProcessor : PageProcessor {
    private val site = Site.me().setRetryTimes(3).setSleepTime(100)

    init {
        site.addHeader("host", "qsm.qoo10.jp")
        site.addHeader("connection", "keep-alive")
        site.addHeader("cache-control", "max-age=0")
        site.addHeader("pgrade-insecure-requests", "1")
        site.addHeader("content-type", "application/json")
        site.addHeader("user-agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36")
        site.addHeader("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
        site.addHeader("accept-encoding", "gzip, deflate, sdch")
        site.addHeader("accept-language", "zh-CN,zh;q=0.8,en;q=0.6,ja;q=0.4,nb;q=0.2,sk;q=0.2,zh-TW;q=0.2")
        site.addHeader("cookie", "")
        site.setDomain("qsm.qoo10.jp")
    }

    override fun process(page: Page) {

    }

    override fun getSite(): Site {
        return site
    }

    companion object {

        @JvmStatic fun main(args: Array<String>) {
            Spider.create(GithubRepoPageProcessor()).addUrl("https://github.com/code4craft").thread(5).run()
        }
    }
}
