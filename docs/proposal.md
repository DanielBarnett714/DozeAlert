---
layout: page
title: Proposal
permalink: /Proposal/
---

<div class="page-background">
    <h1 class="page-title">{{ page.title | escape }}</h1>
</div>

<div class="container">
<div class="section">
    <h1 class="c11" id="h.4lqp25cx7kth">
        <span>Table of Contents</span>
    </h1>
    <ol class="c2 lst-kix_ge7qo3u2btfs-0 start" start="1">
        <li class="c13">
            <span class="c16">Problem Statement and Proposed Solution</span>
        </li>
        <li class="c13">
            <span class="c16">NeedFinding</span>
        </li>
        <li class="c13">
            <span class="c6">Prototyping</span>
        </li>
        <li class="c13">
            <span class="c6">Sample Survey</span>
        </li>
        <li class="c13">
            <span class="c16">Implementation</span>
        </li>
        <li class="c13">
            <span class="c6">Evaluation</span>
        </li>
        <li class="c13">
            <span class="c6">Timeline</span>
        </li>
        <li class="c13">
            <span class="c6">Alternative Solution</span>
        </li>
        <li class="c13">
            <span class="c6">Group Members</span>
        </li>
    </ol>
    <p class="c21">
        <span class="c42 c61 c58 c70"></span>
    </p>
    <h1 class="c11" id="h.6lop3yab93yz">
        <span>Problem Statement</span>
    </h1>
    <p class="c52">
        <span class="c42 c16 c47">Problem Statement</span>
    </p>
    <p class="c52">
        <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
        <span class="c16">Drowsiness is a safety hazard that is plaguing our country. In fact in 2013, the National Highway Traffic Safety
            Administration estimated that drowsy driving was responsible for 72,000 crashes, 44,000 injuries, and 800 deaths</span>
        <sup
            class="c16">
            <a href="#ftnt1" id="ftnt_ref1">[1]</a>
            </sup>
            <span class="c16">. These numbers are underestimated and up to 6,000 fatal crashes may be caused by drowsy drivers. This is obviously
                a problem because sleep deprivation and drowsiness are costing lives, and currently there are </span>
            <span
                class="c16">no</span>
                <span class="c16">&nbsp;clear-cut methods for police officers to identify drowsy drivers</span>
                <sup class="c16">
                    <a href="#ftnt2" id="ftnt_ref2">[2]</a>
                </sup>
                <span class="c16">. They currently use a criterium to determine if a driver is drowsy. By looking at the symptoms like the
                    driver being alone, blood alcohol level being below the legal limit, no sign of brakes being applied,
                    driving too close to the car in front of them, and more, police officers use those signs as indications
                    that that the driver is sleepy. Notice that the items in this criterium do not include a factual number
                    like one that would be provided in a breathalyzer test. Also, drowsiness is considered a genuine safety
                    hazard in the workplace. For example, in the aviation industry, pilots are mandated to have a minimum
                    of eight hours of rest in any 24-hour period of time</span>
                <sup class="c16">
                    <a href="#ftnt3" id="ftnt_ref3">[3]</a>
                </sup>
                <span class="c16">. </span>
                <span class="c16">However, simply giving them the opportunity to rest cannot guarantee that they are actually well rested to
                    perform their high-risk job.</span>
                <span class="c6">&nbsp;Because it cannot be guaranteed, detecting drowsiness is an important step in preserving the safety
                    of the workers and the customers.</span>
    </p>
    <p class="c52">
        <span class="c6">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Ideally we would love to sample from every working professional,
            especially those who have high risk jobs. However, because they are not easily available to us and with the time
            associated in capturing their information, we believe that students are an appropriate sample to draw from. Students
            are very time-crunched and sleep deprived, which can help us categorically identify drowsiness. And our hopes
            is to project that sample onto the working population.</span>
    </p>
    <p class="c31">
        <span class="c6"></span>
    </p>
    <p class="c52">
        <span class="c42 c47 c16">Proposed Solution</span>
    </p>
    <p class="c52">
        <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
        <span class="c16">Our goal is to alert the user when he or she is feeling drowsy. This is in hopes that it will deter the individual
            from performing risky activities like driving, operating a forklift, etc. We foreshadow that this research may
            be of interest to truck drivers or businesses that take risk management very seriously, like the nuclear energy
            industry. They will most likely be the ones who can afford this pricey headset to begin with; however that is
            not to say that with over time, economies of scale will be leveraged and the headsets may become cheaper. Since
            safety is a universally recognized trait, we believe that the students of the University of Rochester will be
            able to provide opinions that are as insightful as individuals who are in the work force. The user would open
            a mobile application that we develop. Then the user would put on a </span>
        <span class="c16">headset</span>
        <span class="c16">, and we would monitor his or her brain waves and blinking patterns. We would allow about 10 seconds to pass from
            the time the user presses the record button before we actually start reading his or her brain waves. This is
            due in part because we believe that a user would be in a more excited state immediately after he or she turns
            on the headset; we want to allow a period of time to pass before we analyze his or her reading. Based on our
            research, we understand that when a person is drowsy, delta and gamma rays are most prominent, while alpha and
            beta waves decline</span>
        <sup class="c16">
            <a href="#ftnt4" id="ftnt_ref4">[4]</a>
        </sup>
        <span class="c16">. By using Fast Fourier Transformation algorithm, we will be able to determine the frequency of each time period.
            Then we will plug those numbers into ratio</span>
        <img src="https://danielbarnett714.github.io/DozeAlert/images/image1.png">
        <span class="c73">&nbsp;</span>
        <span class="c16">, as this ratio has been shown to be significantly higher when in a drowsy state</span>
        <sup class="c16">
            <a href="#ftnt5" id="ftnt_ref5">[5]</a>
        </sup>
        <span class="c16">. If we determine that the user is drowsy, </span>
        <span class="c16">then we would alert the user through the mobile app that he or she should take caution when performing any activity
            that requires his or her attention.</span>
    </p>
    <p class="c28">
        <span class="c16">Our hypothesis is that when the brain waves correspond to alpha or theta waves, we would then look at his or her
            blinking patterns. If the blinking pattern is slow and the user&rsquo;s brain waves are similar to that of alpha
            or theta waves, t</span>
        <span class="c16">hen it would trigger a warning that he or she is in a drowsy state. Because alerting users it not a trivial task,
            we will use our needfinding survey as well as feedback from our prototype sessions to find methods that can best
            and most effectively alert the user. </span>
        <span class="c6">The above ratio has been scientifically proven and makes intuitive sense, but we also plan to reach out to a Brain
            and Cognitive Science (BCS) professor to verify our hypothesis.</span>
    </p>
    <h1 class="c62" id="h.yz0xihd5yxm7">
        <span>Problem Statement </span>
        <span class="c60">Continued</span>
    </h1>
    <p class="c48">
        <span style="overflow: hidden; display: inline-block; margin: 0.00px 0.00px; border: 0.00px solid #000000; transform: rotate(0.00rad) translateZ(0px); -webkit-transform: rotate(0.00rad) translateZ(0px); width: 355.00px; height: 573.50px;">
            <img alt="Proposal.png" src="https://danielbarnett714.github.io/DozeAlert/images/image5.png" style="width: 355.00px; height: 573.50px; margin-left: 0.00px; margin-top: 0.00px; transform: rotate(0.00rad) translateZ(0px); -webkit-transform: rotate(0.00rad) translateZ(0px);"
                title="">
        </span>
    </p>
    <p class="c28">
        <span class="c16">This diagram depicts our solution at a high level. When the user puts on the headset, he or she will open up our
            in-house created application. Immediately after opening the app, when the user presses the &ldquo;Start Recording&rdquo;
            button, we will make a call to the headset via Bluetooth to read the current data from the user&rsquo;s brain.
            Ideally, we want to keep the application running even in the background, so the user can use his or her phone
            while wanting to be monitored. If all signs are normal, then the app will simply continue reading passively.</span>
        <span
            class="c16">&nbsp;Otherwise, if there are signs of drowsiness, then users will be alerted via </span>
            <span class="c16">screen notification, push notification, phone vibration, and alarm. Note that the colors and features are subject
                to change based on feedback from users, following user-centered design.</span>
            <hr style="page-break-before:always;display:none;">
    </p>
    <h1 class="c11" id="h.s88hapy2zhh1">
        <span class="c15">NeedFinding</span>
    </h1>
    <p class="c52">
        <span class="c16 c56">What we are going to use:</span>
    </p>
    <p class="c52">
        <span class="c16">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;We are going to be interviews and surveys. For our interviews, we
            will be targeting both experts and fanatic</span>
        <span class="c16">s. For our expert interview, we plan on reaching out to either Professor Robert Jacobs or Professor Ralf Haefner
            from the BCS </span>
        <span class="c6">department to gather more scientific information, like information regarding the brain and how waves can be measured.
            One of our group members is taking courses with these professors, so there is already rapport built.</span>
    </p>
    <p class="c28">
        <span class="c6">For our fanatic interviews, we would like to interview a public safety administrator and students. In regards to
            the public safety administrator, we want to reach out to Kenneth Grass, Public Safety Patrol Captain. We feel
            that since he is directly responsible for the public safety officers on the ground, he would be a good resource
            to gather the behaviors and attitudes of ground officers. He can give us more insight into what is needed to
            be successful for his line of work and the dangers behind sleep deprivation. We want to capture their information
            because they all perform risky behavior such having to be alert for an extended period of time to perform their
            job successfully or staying up late studying yet continuing to driving while being tired. Our goal from the interviews
            from the fanatics would be to see if there is an understanding of the consequences of drowsiness and whether
            they would engage in alternative, less-risky activities. For example, if students are very drowsy or not in a
            clear state of mind but need to go somewhere, we would want to know if we alert them, would they want to order
            an Uber or Lyft instead.</span>
    </p>
    <p class="c52">
        <span class="c16">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;In addition to interviews, our survey will be distributed to students
            through social media like Facebook. We believe that the students at the University of Rochester (UR) are very
            diverse, and the UR is known for having a lot of international representation. This means that they bring in
            many different experiences that constitute risky behavior from their homelands. We hope to gather more perspective
            and validate our hypothesis that there is a need for our tool. A sample of our survey is attached on the following
            page.</span>
    </p>
    <p class="c21">
        <span class="c0"></span>
    </p>
    <p class="c21">
        <span class="c0"></span>
    </p>
    <p class="c21">
        <span class="c0"></span>
    </p>
    <p class="c21">
        <span class="c0"></span>
    </p>
    <p class="c21">
        <span class="c0"></span>
    </p>
    <p class="c24">
        <span class="c65">Sample Survey</span>
    </p>
    <p class="c18">
        <span style="overflow: hidden; display: inline-block; margin: 0.00px 0.00px; border: 0.00px solid #000000; transform: rotate(0.00rad) translateZ(0px); -webkit-transform: rotate(0.00rad) translateZ(0px); width: 507.00px; height: 725.83px;">
            <img alt="" src="https://danielbarnett714.github.io/DozeAlert/images/image4.png" style="width: 507.00px; height: 725.83px; margin-left: 0.00px; margin-top: 0.00px; transform: rotate(0.00rad) translateZ(0px); -webkit-transform: rotate(0.00rad) translateZ(0px);"
                title="">
        </span>
    </p>
    <h1 class="c54" id="h.qwn05y3qv077">
        <span class="c15">Prototyping</span>
    </h1>
    <p class="c28">
        <span class="c6">Because reaching a state of drowsiness may take a while, the following prototyping will be for the mobile app only,
            and the evaluation of the headset with the application will be outlined in the Evaluation section. The intent
            is to streamline the process while the headset is still being built. Users will be given a cap to wear to mimic
            the headset and the tester playing the computer will react to the user vocalizing when they are drowsy. </span>
    </p>
    <p class="c28">
        <span class="c6">We will start with low fidelity prototype and then gradually iterate into higher fidelity prototyping. For iteration
            1 and iteration 2, the feedback from the participants will be logged via Google Form. We will be asking quantitative
            and qualitative questions regarding the topics in the evaluation column. </span>
    </p>
    <p class="c28">
        <span class="c6">In the final iteration when users are testing the fully programmed Android App, we will take a more hands-off approach
            and have them run through the application. We will also perform a Google Form questionnaire.</span>
    </p>
    <p class="c52">
        <span class="c6">The testing pool will be as follows:</span>
    </p>
    <ul class="c2 lst-kix_958bthi5iell-0 start">
        <li class="c14">
            <span class="c16">3 Recurring Users (</span>
            <span class="c16">longitudinal</span>
            <span class="c6">)</span>
        </li>
    </ul>
    <ul class="c2 lst-kix_958bthi5iell-1 start">
        <li class="c3">
            <span class="c6">These users will be testing the consistency of the designs throughout the semester</span>
        </li>
        <li class="c3">
            <span class="c6">They will be able to provide valuable suggestions regarding the functionalities and aesthetics because they will
                know the background of our research from the beginning of our test</span>
        </li>
    </ul>
    <ul class="c2 lst-kix_958bthi5iell-0">
        <li class="c14">
            <span class="c6">3 Cross Sectional studies</span>
        </li>
    </ul>
    <ul class="c2 lst-kix_958bthi5iell-1 start">
        <li class="c3">
            <span class="c6">These users will be randomly chosen to participate in the iterations.</span>
        </li>
        <li class="c3">
            <span class="c6">Since they will not be familiar with our plan from day one, we expect them to provide feedback on design flaws
                that no one else would notice because they are too deeply invested the project.</span>
        </li>
    </ul>
    <a id="t.2672bcb5dea77ea2e5dbed2069446e7ca58b0553"></a>
    <a id="t.0"></a>
    <table class="c50">
        <tbody>
            <tr class="c10">
                <td class="c49" colspan="1" rowspan="1">
                    <p class="c4">
                        <span class="c20">Iteration</span>
                    </p>
                </td>
                <td class="c37" colspan="1" rowspan="1">
                    <p class="c4">
                        <span class="c20">Type</span>
                    </p>
                </td>
                <td class="c68" colspan="1" rowspan="1">
                    <p class="c4">
                        <span class="c20">Testing Environment</span>
                    </p>
                </td>
                <td class="c53" colspan="1" rowspan="1">
                    <p class="c4">
                        <span class="c20">Evaluation</span>
                    </p>
                </td>
            </tr>
            <tr class="c10">
                <td class="c46" colspan="1" rowspan="1">
                    <p class="c4">
                        <span class="c1">1</span>
                    </p>
                </td>
                <td class="c45" colspan="1" rowspan="1">
                    <p class="c4">
                        <span class="c19">Paper Prototype</span>
                    </p>
                    <p class="c4">
                        <span class="c1">Low fidelity with fantastic breadth (Testing Android App and headset)</span>
                    </p>
                </td>
                <td class="c57" colspan="1" rowspan="1">
                    <p class="c4">
                        <span class="c1">One tester and one user. The tester will ask the user to perform a task. </span>
                    </p>
                </td>
                <td class="c63" colspan="1" rowspan="1">
                    <ul class="c2 lst-kix_ohanngo2692l-0 start">
                        <li class="c4 c33">
                            <span class="c1">Whether or not the process is easy to understand</span>
                        </li>
                        <li class="c4 c33">
                            <span class="c1">Which parts of the design well and what can be improved</span>
                        </li>
                        <li class="c4 c33">
                            <span class="c1">Any other suggestions on improving the application</span>
                        </li>
                    </ul>
                </td>
            </tr>
            <tr class="c10">
                <td class="c46" colspan="1" rowspan="1">
                    <p class="c4">
                        <span class="c1">2</span>
                    </p>
                </td>
                <td class="c45" colspan="1" rowspan="1">
                    <p class="c4">
                        <span class="c19">Interactive Wireframe</span>
                    </p>
                    <p class="c4">
                        <span class="c1">Higher fidelity with greater depth</span>
                    </p>
                </td>
                <td class="c57" colspan="1" rowspan="1">
                    <p class="c4">
                        <span class="c1">One tester and one user. The tester will tell the user what task he or she should perform and will
                            observe. </span>
                    </p>
                </td>
                <td class="c63" colspan="1" rowspan="1">
                    <ul class="c2 lst-kix_dxm979ctgany-0 start">
                        <li class="c4 c33">
                            <span class="c1">Design aesthetics</span>
                        </li>
                        <li class="c4 c33">
                            <span class="c1">Presentation of information</span>
                        </li>
                    </ul>
                </td>
            </tr>
            <tr class="c10">
                <td class="c46" colspan="1" rowspan="1">
                    <p class="c4">
                        <span class="c1">3</span>
                    </p>
                </td>
                <td class="c45" colspan="1" rowspan="1">
                    <p class="c4">
                        <span class="c19">Coded Android App</span>
                    </p>
                    <p class="c4">
                        <span class="c1">Finished product, highest fidelity with greatest depth</span>
                    </p>
                </td>
                <td class="c57" colspan="1" rowspan="1">
                    <p class="c4">
                        <span class="c1">One tester and one user. The tester will silently observe the users with the functional application.</span>
                    </p>
                </td>
                <td class="c63" colspan="1" rowspan="1">
                    <ul class="c2 lst-kix_b0m7ctptq76u-0 start">
                        <li class="c4 c33">
                            <span class="c1">Overall performance</span>
                        </li>
                        <li class="c4 c33">
                            <span class="c1">Design aesthetics</span>
                        </li>
                        <li class="c4 c33">
                            <span class="c1">Interview questions about user experience</span>
                        </li>
                    </ul>
                </td>
            </tr>
        </tbody>
    </table>
    <h1 class="c11" id="h.18kpmxqdos5u">
        <span class="c15">Implementation</span>
    </h1>
    <p class="c23">
        <span class="c0">Doze Alert seems at first like an overly ambitious project to try to implement and succeed. However if we break the
            development process down to three main steps it ends up being a realistic and attainable goal.</span>
    </p>
    <p class="c55">
        <span class="c0">The three steps:</span>
    </p>
    <ul class="c2 lst-kix_7i1jl0d4y9wa-0 start">
        <li class="c22">
            <span class="c0">Accurately and actively record EEG activity from the user in a non-invasive manner.</span>
        </li>
        <li class="c22">
            <span class="c0">Analyze the recorded data in real time to determine whether user is currently alert.</span>
        </li>
        <li class="c22">
            <span class="c0">If the user is not alert, let the user know in a friendly but serious way that the they are drowsy and the task
                they are performing may no longer be safe.</span>
        </li>
    </ul>
    <p class="c23">
        <span>Recording EEG data in a comfortable and non-invasive manner is probably the most important step in the whole process.
            If the user needs to get tape/paste out and hook themselves up to Doze Alert for every single use almost no one
            would want to use it at all. To solve this problem we are planning on using the </span>
        <span class="c72">
            <a class="c25" href="https://www.google.com/url?q=https://shop.openbci.com/collections/frontpage/products/pre-order-ganglion-board?variant%3D13461804483&amp;sa=D&amp;ust=1509406314133000&amp;usg=AFQjCNENTHL9hm44oiGt3NqrtpsrA2lLmA">Ganglion Board</a>
        </span>
        <span>&nbsp;from OpenBCI. OpenBCI (Open Source Brain-Computer Interface) is a company that builds open source biosensing
            boards that can record EEG, EMG, and accelerometer activity. Along with developing the software necessary to
            interact and record data from the BCI, they also release the schematics for the headset that physically holds
            th</span>
        <span>e boards</span>
        <span class="c0">. With these schematics you can 3d print and assemble the headset yourself, which we are planning on doing. You must
            also buy the wiring and dry electrodes for the board&rsquo;s inputs. The Ganglion board is OpenBCI&rsquo;s cheapest
            option, with 4 active channels and an on-board accelerometer. It also comes with a Bluetooth module built in,
            allowing you to connect to the device to your phone/computer wirelessly. </span>
    </p>
    <p class="c23">
        <span>We will be using the OpenBCI Python library to assist in processing the data. The library allows us to stream data
            in a useable format for analyzation and detection of the different waves occurring. It is not clear yet whether
            or not we will need to implement any machine learning algorithms for drowsiness prediction, it may be the case
            where simple algorithms will do the job. From our research so far</span>
        <sup>
            <a href="#ftnt6" id="ftnt_ref6">[6]</a>
        </sup>
        <span>&nbsp;</span>
        <span class="c0">we will be looking for occurrences of Beta waves (12.5 &ndash; 30 Hz) as these are associated with active thinking
            and wakefulness, Alpha Waves (8 &ndash; 12.5 Hz) which are associated with shut eyes and sleep, and for spikes
            that are triggered by eye blinks. We will also make use of the built in accelerometer so it can be determined
            if a person&rsquo;s head is leaned forward or not, or if they&rsquo;re making active movements (for noise reduction).</span>
    </p>
    <p class="c55">
        <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Lastly for the front end we will be building a basic interface using
            Qt and Python. This will theoretically allow us to target as many platforms as possible (Linux, Android, iOS,
            etc&hellip;)</span>
        <span class="c0">.</span>
    </p>
    <h1 class="c11" id="h.87l9tyr6a5p">
        <span class="c15">Evaluation </span>
    </h1>
    <p class="c24">
        <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Testing Doze Alert on users poses a significant challenge. It will
            be simple to test whether or not the app can pick up on whether a person&rsquo;s eyes are closed, but to actually
            test for drowsiness requires the participants to actually be drowsy. Therefore we will probably have to test
            users either early in the morning or late at night, and in the following interview ask if they were feeling tired
            before the experiment or not. We will walk them through a series of tasks, such as them closing their eyes and
            opening them, blinking, and trying to relax. We will then allow the users to experiment with the device themselves,
            so they can get a better feel for the device and its features. From the participation of the user we should be
            able to gather data on accuracy, delay, and make adjustments for a second iteration.</span>
    </p>
    <p class="c24">
        <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
        <span class="c0">After the user is finished we will then follow up with an interview. </span>
    </p>
    <p class="c24">
        <span class="c0">Interview Questions (subject to change):</span>
    </p>
    <ul class="c2 lst-kix_4kp7qdixzjc6-0 start">
        <li class="c13">
            <span class="c0">How was the comfort of the device? </span>
        </li>
        <li class="c13">
            <span class="c0">Will you use Doze Alert while driving?</span>
        </li>
    </ul>
    <ul class="c2 lst-kix_4kp7qdixzjc6-1 start">
        <li class="c24 c75">
            <span class="c0">Why/why not?</span>
        </li>
    </ul>
    <ul class="c2 lst-kix_4kp7qdixzjc6-0">
        <li class="c13">
            <span class="c0">Would you feel safer knowing the person driving you was wearing this device?</span>
        </li>
        <li class="c13">
            <span class="c0">Did you feel the device was effective at detecting drowsiness?</span>
        </li>
        <li class="c13">
            <span class="c0">Did you feel the device was effective at detecting closed eyes?</span>
        </li>
        <li class="c13">
            <span class="c0">What are some suggestions for improving the comfort of the device?</span>
        </li>
        <li class="c13">
            <span class="c0">What suggestions do you have for improving how the app alerts you?</span>
        </li>
        <li class="c13">
            <span class="c0">In your opinion, what features should be included in the app?</span>
        </li>
        <li class="c13">
            <span class="c0">In your opinion, what features should be removed from the app?</span>
        </li>
        <li class="c13">
            <span class="c0">In your opinion, was the design sleek enough?</span>
        </li>
    </ul>
    <p class="c21">
        <span class="c0"></span>
    </p>
    <p class="c21">
        <span class="c0"></span>
    </p>
    <p class="c21">
        <span class="c0"></span>
    </p>
    <p class="c21">
        <span class="c0"></span>
    </p>
    <h1 class="c11" id="h.a5th5zjxfz3">
        <span class="c15">Timeline</span>
    </h1>
    <p class="c31">
        <span class="c38"></span>
    </p>
    <a id="t.a3e62839110af3117cfcdcc0e6c88f9ba1247c9e"></a>
    <a id="t.1"></a>
    <table class="c77">
        <tbody>
            <tr class="c10">
                <td class="c9" colspan="1" rowspan="1">
                    <p class="c4">
                        <span class="c20">Date</span>
                    </p>
                </td>
                <td class="c9" colspan="1" rowspan="1">
                    <p class="c4">
                        <span class="c20">Goal</span>
                    </p>
                </td>
                <td class="c9" colspan="1" rowspan="1">
                    <p class="c4">
                        <span class="c20">Individual(s)</span>
                    </p>
                </td>
            </tr>
            <tr class="c10">
                <td class="c8" colspan="1" rowspan="1">
                    <p class="c4">
                        <span class="c1">October 12th</span>
                    </p>
                </td>
                <td class="c8" colspan="1" rowspan="1">
                    <p class="c4">
                        <span class="c1">1st Proposal</span>
                    </p>
                </td>
                <td class="c8" colspan="1" rowspan="1">
                    <p class="c4">
                        <span class="c1">All</span>
                    </p>
                </td>
            </tr>
            <tr class="c10">
                <td class="c8" colspan="1" rowspan="1">
                    <p class="c4">
                        <span class="c1">October 16th</span>
                    </p>
                </td>
                <td class="c8" colspan="1" rowspan="1">
                    <p class="c4">
                        <span class="c1">Interviews Created</span>
                    </p>
                </td>
                <td class="c8" colspan="1" rowspan="1">
                    <p class="c4">
                        <span class="c1">Dan</span>
                    </p>
                </td>
            </tr>
            <tr class="c10">
                <td class="c8" colspan="1" rowspan="1">
                    <p class="c4">
                        <span class="c1">October 16th</span>
                    </p>
                </td>
                <td class="c8" colspan="1" rowspan="1">
                    <p class="c4">
                        <span class="c1">Surveys Created</span>
                    </p>
                </td>
                <td class="c8" colspan="1" rowspan="1">
                    <p class="c4">
                        <span class="c1">Josh</span>
                    </p>
                </td>
            </tr>
            <tr class="c10">
                <td class="c8" colspan="1" rowspan="1">
                    <p class="c4">
                        <span class="c1">October 17th </span>
                    </p>
                </td>
                <td class="c8" colspan="1" rowspan="1">
                    <p class="c4">
                        <span class="c1">Proposal Edited</span>
                    </p>
                </td>
                <td class="c8" colspan="1" rowspan="1">
                    <p class="c4">
                        <span class="c1">All</span>
                    </p>
                </td>
            </tr>
            <tr class="c10">
                <td class="c8" colspan="1" rowspan="1">
                    <p class="c4">
                        <span class="c1">October 18th</span>
                    </p>
                </td>
                <td class="c8" colspan="1" rowspan="1">
                    <p class="c4">
                        <span class="c1">NeedFinding Started</span>
                    </p>
                </td>
                <td class="c8" colspan="1" rowspan="1">
                    <p class="c4">
                        <span class="c1">Alex</span>
                    </p>
                </td>
            </tr>
            <tr class="c10">
                <td class="c8" colspan="1" rowspan="1">
                    <p class="c4">
                        <span class="c1">October 18th</span>
                    </p>
                </td>
                <td class="c8" colspan="1" rowspan="1">
                    <p class="c4">
                        <span class="c1">Data Collection on Brainwaves Started</span>
                    </p>
                </td>
                <td class="c8" colspan="1" rowspan="1">
                    <p class="c4">
                        <span class="c1">Josh</span>
                    </p>
                </td>
            </tr>
            <tr class="c10">
                <td class="c8" colspan="1" rowspan="1">
                    <p class="c4">
                        <span class="c1">October 18th</span>
                    </p>
                </td>
                <td class="c8" colspan="1" rowspan="1">
                    <p class="c4">
                        <span class="c1">Paper Prototype Completed and Tested</span>
                    </p>
                </td>
                <td class="c8" colspan="1" rowspan="1">
                    <p class="c4">
                        <span class="c1">Alex</span>
                    </p>
                </td>
            </tr>
            <tr class="c10">
                <td class="c8" colspan="1" rowspan="1">
                    <p class="c4">
                        <span class="c1">October 20th</span>
                    </p>
                </td>
                <td class="c8" colspan="1" rowspan="1">
                    <p class="c4">
                        <span class="c1">Data Analysis Started</span>
                    </p>
                </td>
                <td class="c8" colspan="1" rowspan="1">
                    <p class="c4">
                        <span class="c1">Dan</span>
                    </p>
                </td>
            </tr>
            <tr class="c10">
                <td class="c8" colspan="1" rowspan="1">
                    <p class="c4">
                        <span class="c1">October 21st </span>
                    </p>
                </td>
                <td class="c8" colspan="1" rowspan="1">
                    <p class="c4">
                        <span class="c1">Data Collection Complete</span>
                    </p>
                </td>
                <td class="c8" colspan="1" rowspan="1">
                    <p class="c4">
                        <span class="c1">Josh</span>
                    </p>
                </td>
            </tr>
            <tr class="c10">
                <td class="c8" colspan="1" rowspan="1">
                    <p class="c4">
                        <span class="c1">October 21st</span>
                    </p>
                </td>
                <td class="c8" colspan="1" rowspan="1">
                    <p class="c4">
                        <span class="c1">Wireframe Completed and Tested</span>
                    </p>
                </td>
                <td class="c8" colspan="1" rowspan="1">
                    <p class="c4">
                        <span class="c1">Alex</span>
                    </p>
                </td>
            </tr>
            <tr class="c10">
                <td class="c8" colspan="1" rowspan="1">
                    <p class="c4">
                        <span class="c1">October 21st</span>
                    </p>
                </td>
                <td class="c8" colspan="1" rowspan="1">
                    <p class="c4">
                        <span class="c1">App Development Started</span>
                    </p>
                </td>
                <td class="c8" colspan="1" rowspan="1">
                    <p class="c4">
                        <span class="c1">Josh and Dan</span>
                    </p>
                </td>
            </tr>
            <tr class="c10">
                <td class="c8" colspan="1" rowspan="1">
                    <p class="c4">
                        <span class="c1">October 28th</span>
                    </p>
                </td>
                <td class="c8" colspan="1" rowspan="1">
                    <p class="c4">
                        <span class="c1">Data Analysis Complete</span>
                    </p>
                </td>
                <td class="c8" colspan="1" rowspan="1">
                    <p class="c4">
                        <span class="c1">Dan</span>
                    </p>
                </td>
            </tr>
            <tr class="c10">
                <td class="c8" colspan="1" rowspan="1">
                    <p class="c4">
                        <span class="c1">October 29th</span>
                    </p>
                </td>
                <td class="c8" colspan="1" rowspan="1">
                    <p class="c4">
                        <span class="c1">Website Started</span>
                    </p>
                </td>
                <td class="c8" colspan="1" rowspan="1">
                    <p class="c4">
                        <span class="c1">Alex</span>
                    </p>
                </td>
            </tr>
            <tr class="c10">
                <td class="c8" colspan="1" rowspan="1">
                    <p class="c4">
                        <span class="c1">November 2nd</span>
                    </p>
                </td>
                <td class="c8" colspan="1" rowspan="1">
                    <p class="c4">
                        <span class="c1">Mid-semester Review of Final Project</span>
                    </p>
                </td>
                <td class="c8" colspan="1" rowspan="1">
                    <p class="c4">
                        <span class="c1">All</span>
                    </p>
                </td>
            </tr>
            <tr class="c10">
                <td class="c8" colspan="1" rowspan="1">
                    <p class="c4">
                        <span class="c1">November 15th</span>
                    </p>
                </td>
                <td class="c8" colspan="1" rowspan="1">
                    <p class="c4">
                        <span class="c1">App Complete</span>
                    </p>
                </td>
                <td class="c8" colspan="1" rowspan="1">
                    <p class="c4">
                        <span class="c1">Josh and Dan</span>
                    </p>
                </td>
            </tr>
            <tr class="c10">
                <td class="c8" colspan="1" rowspan="1">
                    <p class="c4">
                        <span class="c1">November 15th</span>
                    </p>
                </td>
                <td class="c8" colspan="1" rowspan="1">
                    <p class="c4">
                        <span class="c1">Website Complete</span>
                    </p>
                </td>
                <td class="c8" colspan="1" rowspan="1">
                    <p class="c4">
                        <span class="c1">Alex</span>
                    </p>
                </td>
            </tr>
            <tr class="c10">
                <td class="c8" colspan="1" rowspan="1">
                    <p class="c4">
                        <span class="c1">November 16th</span>
                    </p>
                </td>
                <td class="c8" colspan="1" rowspan="1">
                    <p class="c4">
                        <span class="c1">Evaluation</span>
                    </p>
                </td>
                <td class="c8" colspan="1" rowspan="1">
                    <p class="c4">
                        <span class="c1">All</span>
                    </p>
                </td>
            </tr>
            <tr class="c10">
                <td class="c8" colspan="1" rowspan="1">
                    <p class="c4">
                        <span class="c1">November 17th</span>
                    </p>
                </td>
                <td class="c8" colspan="1" rowspan="1">
                    <p class="c4">
                        <span class="c1">Presentation Complete</span>
                    </p>
                </td>
                <td class="c8" colspan="1" rowspan="1">
                    <p class="c4">
                        <span class="c1">All</span>
                    </p>
                </td>
            </tr>
            <tr class="c10">
                <td class="c8" colspan="1" rowspan="1">
                    <p class="c4">
                        <span class="c1">November 19th</span>
                    </p>
                </td>
                <td class="c8" colspan="1" rowspan="1">
                    <p class="c4">
                        <span class="c1">Presentation Practice</span>
                    </p>
                </td>
                <td class="c8" colspan="1" rowspan="1">
                    <p class="c4">
                        <span class="c1">All</span>
                    </p>
                </td>
            </tr>
            <tr class="c10">
                <td class="c8" colspan="1" rowspan="1">
                    <p class="c4">
                        <span class="c1">November 25th</span>
                    </p>
                </td>
                <td class="c8" colspan="1" rowspan="1">
                    <p class="c4">
                        <span class="c1">Video Complete</span>
                    </p>
                </td>
                <td class="c8" colspan="1" rowspan="1">
                    <p class="c4">
                        <span class="c1">Josh</span>
                    </p>
                </td>
            </tr>
            <tr class="c10">
                <td class="c8" colspan="1" rowspan="1">
                    <p class="c4">
                        <span class="c1">November 25th</span>
                    </p>
                </td>
                <td class="c8" colspan="1" rowspan="1">
                    <p class="c4">
                        <span class="c1">Poster Complete</span>
                    </p>
                </td>
                <td class="c8" colspan="1" rowspan="1">
                    <p class="c4">
                        <span class="c1">Dan</span>
                    </p>
                </td>
            </tr>
            <tr class="c10">
                <td class="c8" colspan="1" rowspan="1">
                    <p class="c4">
                        <span class="c1">November 28th</span>
                    </p>
                </td>
                <td class="c8" colspan="1" rowspan="1">
                    <p class="c4">
                        <span class="c1">Final Presentation</span>
                    </p>
                </td>
                <td class="c8" colspan="1" rowspan="1">
                    <p class="c4">
                        <span class="c1">All</span>
                    </p>
                </td>
            </tr>
            <tr class="c10">
                <td class="c8" colspan="1" rowspan="1">
                    <p class="c4">
                        <span class="c1">December 7th</span>
                    </p>
                </td>
                <td class="c8" colspan="1" rowspan="1">
                    <p class="c4">
                        <span class="c1">Final Project Showcase</span>
                    </p>
                </td>
                <td class="c8" colspan="1" rowspan="1">
                    <p class="c4">
                        <span class="c1">All</span>
                    </p>
                </td>
            </tr>
        </tbody>
    </table>
    <h1 class="c11" id="h.i9kt5g5wyc25">
        <span class="c15">Alternative Solution </span>
    </h1>
    <p class="c28">
        <span class="c6">Admittedly, there are many possible setbacks to this project. Below are a few and how we plan to mitigate them.</span>
    </p>
    <p class="c31">
        <span class="c6"></span>
    </p>
    <p class="c52">
        <span class="c16 c44">Risk 1 - Efficiency of Testing Group</span>
    </p>
    <p class="c28">
        <span class="c6">People may not be incentivized to stay with our longitudinal study.</span>
    </p>
    <p class="c28">
        <span class="c6">Here is our plan on mitigating this risk. There is not much about this that we can think of doing. We could offer
            to buy them Starbucks for their time but as the semester goes on, students get busier. We hope that they do not
            drop out of our study.</span>
    </p>
    <p class="c31">
        <span class="c6"></span>
    </p>
    <p class="c52">
        <span class="c44 c16">Risk 2 - Project Completely Not Working</span>
    </p>
    <p class="c28">
        <span class="c16">We do not have extensive background in brain wave modeling, so despite research from online resources, we cannot
            guarantee that this will be implemented the way that we picture it in our heads. This is probably our biggest
            risk.</span>
        <sup>
            <a href="#cmnt2" id="cmnt_ref2">[b]</a>
        </sup>
        <span class="c6">&nbsp;Fortunately, however there is a lot of information online that we have at our disposal. And there are a lot
            of open source information regarding OpenBCI that we can learn from as well. We know for certain that the boards
            we ordered from the company can detect brain waves, blink, and tilt, which are all we need for the project&rsquo;s
            success.</span>
    </p>
    <p class="c28">
        <span class="c16">However, we still want to make sure that we minimize the risk. </span>
        <span class="c16">We first will talk with a BCS professor to gain more insights and knowledge about the brain</span>
        <span class="c16">. Secondly, since the biggest technological risk comes from the headset and calling an API to OpenBCI, we would need
            to substitute this step out if the headset does not work as planned. We would eliminate the use of the headset
            and instead opt for vision as our source of data. With a camera in front of a person, we could detect facial
            features and muscles to make determinations. At that point, features of the application will need to be changed
            and updated. This aligns with what we are exposed to in lectures so it would be a safer alternative. Lastly,
            our timeline is a bit rushed but the reason for that is because we want to ensure that we have adequate time
            to </span>
        <span class="c16">pivot and make necessary changes.</span>
    </p>
    <p class="c28">
        <span class="c6">Another alternative that was presented to us is to solely measure blinking patterns to detect drowsiness. Measuring
            eye blinks via a headset is possible and this could serve as an alternative. We could then measure blink rate
            as a determinant and alert the user.</span>
    </p>
    <h1 class="c11" id="h.i85ws8se9wc5">
        <span>Group Members</span>
    </h1>
    <p class="c31">
        <span class="c38"></span>
    </p>
    <a id="t.3f0c57992940d0c56d12f758a02baf19936df9f8"></a>
    <a id="t.2"></a>
    <table class="c66">
        <tbody>
            <tr class="c10">
                <td class="c35" colspan="1" rowspan="1">
                    <p class="c4">
                        <span class="c39 c16">Picture</span>
                    </p>
                </td>
                <td class="c35" colspan="1" rowspan="1">
                    <p class="c4">
                        <span class="c39 c16">Name</span>
                    </p>
                </td>
                <td class="c35" colspan="1" rowspan="1">
                    <p class="c4">
                        <span class="c16 c39">Field(s) of Study</span>
                    </p>
                </td>
                <td class="c35" colspan="1" rowspan="1">
                    <p class="c4">
                        <span class="c39 c16">Role</span>
                    </p>
                </td>
                <td class="c35" colspan="1" rowspan="1">
                    <p class="c4">
                        <span class="c39 c16">Skills</span>
                    </p>
                </td>
            </tr>
            <tr class="c10">
                <td class="c17" colspan="1" rowspan="1">
                    <p class="c4">
                        <span style="overflow: hidden; display: inline-block; margin: 0.00px 0.00px; border: 0.00px solid #000000; transform: rotate(0.00rad) translateZ(0px); -webkit-transform: rotate(0.00rad) translateZ(0px); width: 110.00px; height: 110.67px;">
                            <img alt="" src="https://danielbarnett714.github.io/DozeAlert/images/image6.jpg" style="width: 110.00px; height: 110.67px; margin-left: 0.00px; margin-top: 0.00px; transform: rotate(0.00rad) translateZ(0px); -webkit-transform: rotate(0.00rad) translateZ(0px);"
                                title="">
                        </span>
                    </p>
                </td>
                <td class="c17" colspan="1" rowspan="1">
                    <p class="c4">
                        <span class="c6">Alex Mai</span>
                    </p>
                </td>
                <td class="c17" colspan="1" rowspan="1">
                    <p class="c4">
                        <span class="c6">Computer Science, Business</span>
                    </p>
                </td>
                <td class="c17" colspan="1" rowspan="1">
                    <p class="c4">
                        <span class="c6">UI Design, Research, Development (if needed)</span>
                    </p>
                </td>
                <td class="c17" colspan="1" rowspan="1">
                    <p class="c4">
                        <span class="c6">Java, basic Python, HTML, CSS, JavaScript, Swift</span>
                    </p>
                </td>
            </tr>
            <tr class="c10">
                <td class="c17" colspan="1" rowspan="1">
                    <p class="c4">
                        <span style="overflow: hidden; display: inline-block; margin: 0.00px 0.00px; border: 0.00px solid #000000; transform: rotate(0.00rad) translateZ(0px); -webkit-transform: rotate(0.00rad) translateZ(0px); width: 110.00px; height: 110.67px;">
                            <img alt="" src="https://danielbarnett714.github.io/DozeAlert/images/image3.jpg" style="width: 110.00px; height: 110.67px; margin-left: 0.00px; margin-top: 0.00px; transform: rotate(0.00rad) translateZ(0px); -webkit-transform: rotate(0.00rad) translateZ(0px);"
                                title="">
                        </span>
                    </p>
                </td>
                <td class="c17" colspan="1" rowspan="1">
                    <p class="c4">
                        <span class="c6">Daniel Barnett</span>
                    </p>
                </td>
                <td class="c17" colspan="1" rowspan="1">
                    <p class="c4">
                        <span class="c6">Computer Science</span>
                    </p>
                </td>
                <td class="c17" colspan="1" rowspan="1">
                    <p class="c4">
                        <span class="c6">Research, Development, Design</span>
                    </p>
                </td>
                <td class="c17" colspan="1" rowspan="1">
                    <p class="c4">
                        <span class="c6">Basic Neuro Computation, Python, Java</span>
                    </p>
                </td>
            </tr>
            <tr class="c10">
                <td class="c17" colspan="1" rowspan="1">
                    <p class="c4">
                        <span style="overflow: hidden; display: inline-block; margin: 0.00px 0.00px; border: 0.00px solid #000000; transform: rotate(0.00rad) translateZ(0px); -webkit-transform: rotate(0.00rad) translateZ(0px); width: 110.00px; height: 110.67px;">
                            <img alt="" src="https://danielbarnett714.github.io/DozeAlert/images/image7.jpg" style="width: 110.00px; height: 110.67px; margin-left: 0.00px; margin-top: 0.00px; transform: rotate(0.00rad) translateZ(0px); -webkit-transform: rotate(0.00rad) translateZ(0px);"
                                title="">
                        </span>
                    </p>
                </td>
                <td class="c17" colspan="1" rowspan="1">
                    <p class="c4">
                        <span class="c6">Joshua Churchin</span>
                    </p>
                </td>
                <td class="c17" colspan="1" rowspan="1">
                    <p class="c4">
                        <span class="c6">Computer Science</span>
                    </p>
                </td>
                <td class="c17" colspan="1" rowspan="1">
                    <p class="c4">
                        <span class="c6">Research, Development, Design</span>
                    </p>
                </td>
                <td class="c17" colspan="1" rowspan="1">
                    <p class="c4">
                        <span class="c6">Java, C, C++, HTML, CSS</span>
                    </p>
                </td>
            </tr>
        </tbody>
    </table>
    <p class="c32">
        <span class="c16">Note that we have a three-person team, thus there will be quite an overlap between some responsibilities. We did,
            however, attempt to separate overall responsibilities via the timeline and deliverables page.</span>
    </p>
    <hr class="c74">
    <div>
        <p class="c12">
            <a href="#ftnt_ref1" id="ftnt1">[1]</a>
            <span class="c40 c43">&nbsp;https://www.cdc.gov/features/dsdrowsydriving/index.html</span>
        </p>
    </div>
    <div>
        <p class="c12">
            <a href="#ftnt_ref2" id="ftnt2">[2]</a>
            <span class="c40 c43">&nbsp;https://www.ncbi.nlm.nih.gov/pmc/articles/PMC3571819/</span>
        </p>
    </div>
    <div>
        <p class="c12">
            <a href="#ftnt_ref3" id="ftnt3">[3]</a>
            <span class="c40 c43">&nbsp;https://www.faa.gov/news/fact_sheets/news_story.cfm?newsId=6762</span>
        </p>
    </div>
    <div>
        <p class="c12">
            <a href="#ftnt_ref4" id="ftnt4">[4]</a>
            <span class="c40 c43">&nbsp;John M Stern, Atlas of EEG patterns, Lippincott Williams &amp; Wilkins, pp. 27-55, 2005.</span>
        </p>
    </div>
    <div>
        <p class="c12">
            <a href="#ftnt_ref5" id="ftnt5">[5]</a>
            <span class="c40 c43">&nbsp;B. T. Jap, S. Lal, P. Fischer, E. Bekiaris, &quot;Using EEG spectral components to assess algorithms for
                detecting fatigue&quot;, Expert Systems with Applications, vol. 36, no. 2, pp. 2352-2359, 2009.</span>
        </p>
    </div>
    <div>
        <p class="c12">
            <a href="#ftnt_ref6" id="ftnt6">[6]</a>
            <span class="c43">&nbsp;</span>
            <span class="c5">
                <a class="c25" href="https://www.google.com/url?q=https://en.wikipedia.org/wiki/Beta_wave&amp;sa=D&amp;ust=1509406314156000&amp;usg=AFQjCNFYDta2l_5PTRcY_T6hXViIHFO4wg">https://en.wikipedia.org/wiki/Beta_wave</a>
            </span>
            <span class="c40 c43">, https://en.wikipedia.org/wiki/Alpha_wave</span>
        </p>
    </div>
</div>
</div>