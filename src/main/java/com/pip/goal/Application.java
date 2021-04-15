package com.pip.goal;

import java.io.File;
import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

@SpringBootApplication
public class Application implements CommandLineRunner {

	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	BarChartGenerator barChartGenerator;

	@Autowired
	LineChartGenerator lineChartGenerator;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) {

		try {
			barChartGenerator.generate();
			lineChartGenerator.generateLineChart();
			sendEmailWithAttachment();

		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	void sendEmailWithAttachment() throws MessagingException, IOException {
		System.out.println("Sending Email...");
		MimeMessage msg = javaMailSender.createMimeMessage();

		MimeMessageHelper helper = new MimeMessageHelper(msg, true);
		helper.setTo("nagulsaida.shaik@cognizant.com");

		helper.setSubject("Daily Trend Charts");

		helper.setText("<h1> Please Check the attachment </h1>", true);

		FileSystemResource lineFile = new FileSystemResource(
				new File("C:\\siva\\PIP\\pip-goals\\src\\main\\resources\\LineChart_dailyTrend.jpeg"));
		FileSystemResource barFile = new FileSystemResource(
				new File("C:\\siva\\PIP\\pip-goals\\src\\main\\resources\\BarChart_dailyTrend.jpeg"));

		helper.addAttachment("BarChart_DailyTrend.jpeg", barFile);
		helper.addAttachment("LineChart_dailyTrend.jpeg", lineFile);

		javaMailSender.send(msg);
		System.out.println("Mail sent successfully...");

	}
}
