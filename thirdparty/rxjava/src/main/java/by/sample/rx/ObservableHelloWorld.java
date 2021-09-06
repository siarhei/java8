/* Copyright © 2021 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
  CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.
 */
package by.sample.rx;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.*;
import io.reactivex.rxjava3.disposables.Disposable;

/**
 * @author sshchahratsou
 */
public class ObservableHelloWorld {
    public static void main(String[] args) {
        Observable<String> observable = Observable.create(new ObservableSubscribeHW());
        observable.subscribe(new Subscriber());
    }
}

class ObservableSubscribeHW implements ObservableOnSubscribe<String> {
    @Override
    public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Throwable {
        emitter.onNext("Hello World");
        emitter.onComplete();
    }
}

class Subscriber implements Observer<String> {
    @Override
    public void onNext(@NonNull String value) {
        System.out.println(value);
    }

    @Override
    public void onError(@NonNull Throwable error) {
        error.printStackTrace(System.out);
    }

    @Override
    public void onComplete() {
        System.out.print("On Complete");
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {

    }
}