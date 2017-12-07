---
layout: page
title: Needfinding
permalink: /needfinding/
---

<div class="page-background">
	<h1 class="page-title">{{ page.title | escape }}</h1>
</div>


<div class="container">
<h2>Overview</h2>
<p>Our needfinding methodology is tri-fold. We want to target University of Rochester resources to be our test subjects, since they are the most convenient to us. Thus, our first step was to survey the general student population. Secondly, we interviewed a few students. And lastly, we interviewed a professor from the Brain and Cognitive Science department.</p>

<br>
<hr>

<h2>Survey Information</h2>
<p style="margin-bottom:10px;">On November 1, 2017, we pushed out a short and simple survey consisting of eight questions to the general student population. We posted in Class of 2018, Class of 2019, Class of 2020, and Class of 2021 Facebook pages. In addition, we emailed the survey to friends whom do not have a Facebook account, to get their perspectives as well. The goal of the survey was to ascertain the behaviors and attitudes towards driving and to find out if there are specific needs that have not yet been addressed. As of November 9, 2017, we received 52 responses.</p>

<p style="margin-bottom:10px;">Around 55.77 percent of respondents said that they normally drive in New York state. This is no surprise because the University of Rochester is located in New York, and many of its students have cars on campus. The other 45 percent were fragmented across other states, and only one respondent stated that he or she drives mostly in Mumbai, India. This is relevant to us because we now know that the opinions that the people are expressing are mostly based on driving experiences in the United States.</p>

<div class="center">
	<img src="../images/needfinding/hours-tired.jpg" alt="Bar chart of hours tired while driving" class="survey-image">
</div>
<div class="center">
	<img src="../images/needfinding/opinion-driving-drowsy.jpg" alt="Opinion on driving while drowsy" class="survey-image">
</div>

<p style="margin-bottom:10px;">Another key insight that we discovered was that about 78.8 percent of the respondents stated that they spent at least one hour driving while drowsy, with one hour being the highest response (34.6 percent) and 2 hours (23.1 percent) being the second highest response. In addition, we also discovered that the majority of people believe that driving while drowsy is not okay. We posed the response to the question in form of a linear scale from Strongly Disagree to Strongly Agree. It turns out that 64 percent of the respondents disagreed or strongly disagreed with the statement that driving while drowsy is okay. And when asked about their opinion on whether driving while tired is a risk, 48 of the 52 individuals stated yes, with a variety of responses. Some said yes simply because it impairs reaction time, while others shared personal stories like knowing a friend who fell asleep on the wheel at 70+ miles per hour and hitting a median. All the feedback from these respondents point to the fact that there really is a need that is worth solving.</p>

<div class="center">
	<img src="../images/needfinding/keep-phone.jpg" alt="Chart showing where most people keep their phones while driving" class="survey-image">
</div>
<div class="center">
	<img src="../images/needfinding/phone-attention.jpg" alt="Notification method that gets users most attention" class="survey-image">
</div>

<p style="margin-bottom:10px;">Finally, another fact that we wanted to ascertain in our survey was how to best alert the user. Thus, we asked a question about where a person typically puts his or her phone while driving and the best method that his or her phone gets his or her attention. We discovered that 78.8 percent of the people surveyed keep their phone in the cup holders. And sound and phone vibration typically gets their attention the easiest. We will use these findings when developing our prototypes and final application.</p>
<a href="https://drive.google.com/file/d/1Huaigdoe7WHFo9XucB5kjA8qLeJ_2CF2/view?usp=sharing" class="btn-large waves-effect waves-light green" target="_blank" style="margin-top: 20px;">Raw Survey Results</a>
<br>
<hr>

<h2>End User Interviews</h2>
<p style="margin-bottom:10px;">A total of three end-user interviews were conducted. The purpose of these interviews was to gain further understanding of the qualitative side of the responses to the survey. These interviews were conducted individually and in a casual setting in order to ensure that the interviewees were comfortable and did not feel judged or criticized of their opinions and beliefs on driving. We chose to interview people who held valid driver license and have driving experience, because we wanted to see if actual drivers could picture themselves using a device like ours. An individual who do not have driving experience, for example, may not know common struggles of driving like forgetting to release the emergency brakes on a car before pressing on the gas pedal, and more. Thus, it was important to target people who actually drive.</p>

<p style="margin-bottom:10px;">From the interviews, there were four key takeaways. All three interviewees unequivocally said that the biggest fear that they have when driving is getting into an accident. Secondly, they all said, to some variation, that driving while sleepy, tired, or drowsy is “wrong and dangerous.” Thirdly, they said that they would be willing to rest if they were notified that they were drowsy. This comes with a condition, however. All of them agreed that the application is a great idea, but they would be less likely to take a nap if the commute to their destination is short, for example like a 15-minute drive to the grocery store. But their corrective action would be to just drive more cautiously. And when the commute is long, at least 30 minutes, they would be more inclined to pull over at a rest stop or hotel to get some sleep. Finally, we also discovered that despite the risk in looking at one’s phone while driving, people will still do it anyways. Two of our interviewees check their phones when they are at a stop, and one of them checks his phone when driving on a highway with a straight road. These findings were very interesting, and they buttress the motivation of our project.</p>
<a href="https://drive.google.com/file/d/1HdphMS85DcMcbI1iBE8Q1MJRaZKOjHLF/view?usp=sharing" class="btn-large waves-effect waves-light green" target="_blank" style="margin-top: 20px;">Raw Interview Questions</a>
<br>
<hr>

<h2>Expert Interview</h2>
<p style="margin-bottom:10px;">For the final step in our needfinding process, we interviewed Professor Ralph Haefner from the BCS department. The purpose of this interview was to gain a better understanding of the brain and of methods to analyze data coming from EEG signals. Daniel is a student of Professor Haefner, so it was convenient to get his perspective on the viability of our project.</p>

<p style="margin-bottom:10px;">Although Professor Haefner had not heard of OpenBCI, which is the open source library we using to manipulate the brain data, he seemed very interested in our project. During the interview, he mentioned that using EEG signals are great in trying to determine if someone is drowsy. Of course, there are other methods, but the brain is a reliable source for assessing attention. He proposed a few methods of analyzing data including using a fast Fourier transformation algorithm, which validates our initial hypotheses. And finally, he believes that our project is possible to achieve, and stresses the importance of finding the most non-obtrusive method of alerting the user while driving.</p>

</div>
