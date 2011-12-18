package ch03

import org.apache.mahout.cf.taste.impl.common.FastByIDMap
import org.apache.mahout.cf.taste.impl.model.GenericDataModel
import org.apache.mahout.cf.taste.impl.model.GenericUserPreferenceArray
import org.apache.mahout.cf.taste.model.DataModel
import org.apache.mahout.cf.taste.model.PreferenceArray

object CreateGenericDataModel extends App {

  val preferences = new FastByIDMap[PreferenceArray]
  val prefsForUser1 = new GenericUserPreferenceArray(10)
  prefsForUser1.setUserID(0, 1L)
  prefsForUser1.setItemID(0, 101L)
  prefsForUser1.setValue(0, 3.0f)
  prefsForUser1.setItemID(1, 102L)
  prefsForUser1.setValue(1, 4.5f)
  preferences.put(1L, prefsForUser1)

  val model = new GenericDataModel(preferences)
  println(model)
}

