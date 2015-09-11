Best Practices
==============

It would be nice if you could call someone up and they could just looking at a resume or calling a reference
tell if such a person was an expert android developer to or not, right?  Certainly, the temp agencies and
recruiters are counting on you relying upon that assumption, even though it is false.

In code auditing 50+ 3rd party libraries as I want to implement a certain design strategy on top of
Google's Material Design I have found that one really cannot rely upon any resume or work reference as
an indication of a guarantee of that developer employing the best practices and manual habits and manual
choices required to ensure security of user data and application data, maintainability of code,
testability of code, and the guarantee that said application will execute in a reliable fashion on the
Android OS versions it targets. At the most I came up with the figure of 1% of the android developers
found on github Ic an rely upon as far as using their android application development libraries with
no bad effects as far ensure security of user data and application data, maintainability of code,
testability of code, and the guarantee that said application will execute in a reliable fashion on the
Android OS versions it targets.

So what are you to do as the co-founder of a Start-Up when you need to find an android developer?

My suggestion would be to engage potential android developer candidates in a conversation about a code
repo of theirs on github or bitbucket about the manual processes they followed and how they show up in
that git repo. I am going to pretend that you took advantage of my Coding Saturdays and traveled to my
location and sat down by me to watch me code and explain my manual best practices android application
development processes AND THUS EXPLAIN and HOPEFULLY VISUALLY PRESENT examples of my manual best practices
to the android application development process.

1. Targeting multiple Android OS versions

On the Android OS platform we target a minimum and maximum Android OS version and manually mark with
TargetAPI annotations the places where a class or method is not called until its on a certain OS version as
checked usually by if Build.OS.VERSION statements. If we want a new feature with the older Android OS
versions than we write some code to do it a different way for the older OS version.

But, if we do not have that habit of manually checking the code for OS compatibility across all targeted
OS versions than we end up rewriting application code multiple times and lengthy debugging sessions.
And if not following that essential good manual best practices developer habit than it gets somewhat
hard for me to predict a PRODUCT SHIP DATE.

2. Unchecked Generics

Java uses a system within its compiler to assist the developer in making sure the right object is using the
right type called Generics. Generally, unchecked generics make the java compiler do a soft fail. Thus, I as
an expert java developer have to go through and manually check each unchecked generics warning in the IDE and
make a decision about whether to implement Generics for that java object or mark that method and or object
call as an unchecked suppresswarnings annotation so that the compiler compiles that code correctly.

3. Performance Optimization

To improve UI rendering I reduce ViewGroups within a layout file and the number of views within a ViewGroup and
I use my own modified version of ViewServer which hooks up with a SDK tool so that I can see where I need
to make optimizations.  If there are many optimizations I end up extending one of the Layout classes and
add code for that particular case.

The Lint tool runs in the IDE and shows performance warnings within the view of the class file. I than have to
manually examine each warning and make the correct choices in performance optimization based on the the
characteristics of the code and the application. For example, on android we have some fast math libraries that
execute faster on those java VM's that are register based such as Dalvik/ART than the main java packaged ones
and so the Lint tool warns me when I attempt to use math api calls out of the main java package.

The compiler also through the IDE will give warnings about language optimizations. These mainly consist of
waring about a language usage choice that may result in better performance. These choices are manual
as with the Android OS platform at any time the OS versions can span across multiple versions of
Java which measn not every java feature is available in older Android OS versions. For example,
Android OS Version 15 through Android OS Version 23 uses Java version 6 and 7 whereas in the future of
Android OS version 20 through 25 it will be Java version 7 and 8.

There is also platform quirks that one makes a development choice on. For example, in the time API Joda jars it
references resources in the Jar. Thus, to use on android we have to convert it manually to an android
library with resources so that the resource look-up is faster.


4. Application and Component Lifecycles

My manual best practices habits show up in the context of application and component lifecycles in the
form of application debugging and logging and annotation marking I complete when developing an application.
Thus, I am just going to pick one of the demo applications in this repo and show you some visuals of the
debugging and logging that I do to keep track of the application and component lifecycles.

So we have this small GWSBlurringViewDemo that shows some images and than shows those images being
blurred:

[GWSBlurringViewDemo Video]()

Real simple application.  I use some 3rd party libraries and gradle plugins to make this easier,
mainly Hugo, Timber, and LeakCanary. Once setup via gradle and the application class onCreate method and
the Activity onCreate method I am than using Hugo to annotate the methods I want debug logged as
the Hugo library is  meant to be used as debugging tool.

I use Timber as my Application logging tool as it ensures that application logging is separated from
non-production logging.

Than I use LeakCanary to track the component lifeecycles such as the activity and fragments.  A sample of the
code setup is this:






Than the logs produced look like this:





But, remember folks I still have that underlying correct objects anf field access choices that rear their
ugly head in that I have to manually verify that I do not accidentally leak user or device data. At this
layer its, assuming correct object and field access choices, a matter of ensuing that I am logging to
debug when required and only information required to debug and not user data.








10. Predictable By Design

With Google changing is Design Best Practices every 36 months as far as aesthetics and the release of
a major version of Android every year its becomes important to have a set of manually processes that
guarantee a design palette of UI components that can be mixed and matched to brand an application. If
I take the pain upfront and code audit a bunch of 3rd party libraries and modify them for that purpose
when a new Android OS major version is released I can back port the new features and combine it with the
UI component palette libraries I have and develop a unique brand strategy in the form of the design of the
android application. Because I have taken the pain upfront to modify the 3rd party libraries to maintain
them under these circumstances I have a predictable situation where I can tell the product manager when
the product will ship.  This is what engineering is about, its not always a neat innovation but often
very manual efforts managed into habits that create a predictable stable product.

You see I ask for you to start-up co-founder to engage in conversation and think about the possibility
of me telecommuting as an android developer for your start-up as I am somewhat worth it in terms of the
best development and design practices that I manually follow as deeply ingrained habits. In this current
situation with not enough exp android developers that something that a temp agency or recruiter cannot
provide to you and fails at providing to you.