package id.nns.myjetpackcompose.di

import id.nns.myjetpackcompose.util.RealmUtils

object Injection {

    fun provideRealmUtils() : RealmUtils {
        return RealmUtils.getInstance()
    }

}