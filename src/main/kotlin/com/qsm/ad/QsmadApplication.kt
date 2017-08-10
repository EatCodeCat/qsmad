package com.qsm.ad

import org.springframework.boot.SpringApplication
import org.springframework.boot.SpringBootConfiguration
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.ComponentScan


@ComponentScan
@EnableAutoConfiguration
//@EnableEurekaClient
@SpringBootConfiguration
@SpringBootApplication
class QsmadApplication


fun main(args: Array<String>) {
    SpringApplication.run(QsmadApplication::class.java, *args)
    
}
