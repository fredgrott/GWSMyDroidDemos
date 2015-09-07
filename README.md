GWSMyDroidDemos
===============

This code repo serves two purposes. One, to soft fork some libraries I use that needed some code cleanup.
Two, to provide one place to put my UI control demos and application demos. In this readme you will
see links to the videos of the demos and in the application demos case, sometimes apk file links.

Best Practices
==============

1. Code audit the 3rrd party libraries(non-Google) you use as most labeled android developers do not
   produce reliable code. They miss either simple stuff like checking and resolving all soft errors
   indicated by the IDE(we have to do that as we are targeting a range of api versions), putting code on
   the wrong thread(kind of important in GUI like applications), or just plain commit code with hard errors.
   How do I know this? Because after cleaning 50+ libraries I am convinced that you cannot measure an
   android developer by resume/and or references but instead have to know what processes they folow as most of
   the processes that guarantee tha tmy code is reliable are not automatic but human manual.
2. Material Design
3. Code optimization for Davlik/ART and SMP(multi-cpu devices).
4. Coding to the proper Thread.
5. Catching Memory Leaks, context and otherwise.
6. Proper Debug Logging and privatizing fields so no user information leaks in production builds as user
   enemies love to root a stolen device.
7. Daily builds with best gradle build practices followed and commit to git with no effing hard errors!
8. Cover only one mobile platform at a time. Why? 50 3rd party libraries code cleaned plus the android support
   libraries just to backport material design feature to get a consistent look and feel across all
   Android OS versions in the marketplace.



UI Control Demos
================

Every 18 to 28 months Google, just like Apple, introduces a new UI set of best practices and
new UI features in a major version Android OS release.  Thus, it is important to understand how to
port features backward to prior OS versions as Google only backports the features it feels are
important.

The UI demos below cover most of the new feature effects of Material backported from the Lollipop and
Marshmallow version releases.

Application Demos
=================

These application demos show a full application in terms of what branding can be done. Branding is important
as we do not have the same massive room of desktop applications to implement branding. But, you are probably
viewing these demos because you want an android developer that customizes UI controls to brand. Hence
this set of application demos.

##Sammy Maven



My Rant About Finding An Android Developer(Recruiters are Doing IT WRONG)
================

The recruiter will not find you a polished android developer the manual human processes that are not
automatic are never listed on the resume or found via reference checks. You as the start-up co-founder
have to ask questions about the manual processes that are used hourly and daily in building an android
application because if those process are not being followed because the android developer is new and
did not bother to emulate the expert android developer than that produces messy code that is hard to
debug, maintain, and rely upon when the product manager is attempting to produce a schedule to get
the product out the door.

Those in the Greater Chicago Land Area hve it easier in that they can actually visit me on Saturday
and see my  manual developer processes in action while I code.


My Manual Android Development Processes

1. Targeting Multiple Versions of the Same API on different OS Versions

I use if Build statements to detect the OS version and call a specialized method that may have been
updated for that version. Because the method is not referenced until its called it will compile correctly.
But, we still mark them with a @TargetApi annotation. HOWEVER, the IDE only soft warns about new api calls
that are not in the minimum api target and a developer is suppose to track down each soft warning in the
IDE and ensure it was bracketed by if Build statements to call on the correct behavior when we
cannot use the api in levels before it and do the correct call when we can.

2. Unchecked Generics

Java uses a system called generics to make sure that we use objects with their correct types. If an
object type is unchecked than our IDE will give a soft warning but the compiler will still do a
hard warning refusing to compile it. So the developer must track down each generic soft error warning
in the IDE generic-fy that method call or if its not important suppress the generic warning so that the
compiler will still compile it.

3 Manually Debugging Activity and Fragment Lifecycle Context Memory Leaks

That is a mouthful, huh? There are some 3rd party libs and gradle plugins to help but one is still
writing log statements to track the activity or fragment lifecycle and track context objects to
catch memory leaks and wrong lifecycle assumptions. Even with the 3rd party libs and plugins its still
a manual process of writing log statements and checking the logs to see what was logged and than
resolving anything that crops up.

And this is what it looks like:

![ide no errors](/readme_images/ide_no_errorsxcf.png)

For the untrained you want the android developer that is using some best practices manual development
processes to create maintainable, testable, and predictable android native java code and to your untrained
eye you will see @Annotations and or comments with each manual process verifying the correct developer
best practice process and choice.

There are many more, and those items are not on any resume or reference check. Now, you know why I usually
ask Greater ChicagoLand Area start-ups to come to my location on a Saturday and see me code and my manual
development processes.



About Fred Grott
================

You probably notice that I do things differently as far as code development. My training in code
development comes from having a hands-on-imperative to electronics and code from an early age and
intense passionate interest.  But, its not a free-wheeling process in that I measure myself against
expert developers much like a martial artist like Bruce Lee faced each new area of training and
sparring with other martial artist experts.

I explore things all the time and I am an computer language polyglot. That does not mean that I can
be bothered and spammed to do someone's back-end or front-end in ruby, python, or java as I am
now focused on android native java application development. Some of my explorations have even been
in such diverse subjects as breaking RSA encryption.

I took my years of experience in start-ups on back-ends, front-ends, and mobile java and transition to
developing and designing native java applications for the android OS mobile platform.


Contacting
==========

Same rules as is on my LinkedIN profile, no recruiters and only funded(beyond speculation seed capital stage)
startups and it would be helpful to view the demos and ask about the code as you would standout from
all the freeloaders who want to exploit that spam me. My email is fred dot grott at gmail dot com.
