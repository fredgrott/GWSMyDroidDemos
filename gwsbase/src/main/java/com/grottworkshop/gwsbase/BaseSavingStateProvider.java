package com.grottworkshop.gwsbase;

/**
 * Extend this class:
 *
 * <code>
 *     public final class MyExtendedBaseSavingStateProvider extends BaseSavingStateProvider{
 *         private static final MyExtendedBaseSavingSate mySavingState = new MyExtendedBaseSavingState(objectGlobalOne, objectGlobalTwo);
 *
 *         public static MyExtendedBaseSavingState getInstance(){
 *             return mySavingState;
 *         }
 *
 *         private MyExtendedSavingStateProvider(){
 *
 *         }
 *
 *     }
 * </code>
 *
 * than in ExtendedAppClass
 *
 * <code>
 *     initGlobalSingletons(){
 *         MyExtendedBaseSavingStateProvider.getInstance();
 *     }
 * </code>
 *
 * Due to the way we call our actual GlobalState holders through this which is
 * called in our extended appclass, the globalstate holders are also singletons by the nature of
 * the calls.
 * Created by fgrott on 9/22/2015.
 */
@SuppressWarnings("unused")
public final class BaseSavingStateProvider {
}
