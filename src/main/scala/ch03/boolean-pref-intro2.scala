package ch03

import collection.JavaConverters._

import java.io.File
import org.apache.mahout.cf.taste.impl.eval._
import org.apache.mahout.cf.taste.eval.RecommenderBuilder
import org.apache.mahout.cf.taste.impl.model.file._
import org.apache.mahout.cf.taste.impl.model.GenericBooleanPrefDataModel
import org.apache.mahout.cf.taste.impl.neighborhood._
import org.apache.mahout.cf.taste.impl.recommender._
import org.apache.mahout.cf.taste.impl.recommender.slopeone._
import org.apache.mahout.cf.taste.impl.similarity._
import org.apache.mahout.cf.taste.model._
import org.apache.mahout.cf.taste.eval.DataModelBuilder
import org.apache.mahout.cf.taste.neighborhood._
import org.apache.mahout.cf.taste.recommender._
import org.apache.mahout.cf.taste.similarity._
import org.apache.mahout.cf.taste.impl.common.FastByIDMap
import org.apache.mahout.common.RandomUtils

/** 3.3.3 Selecting compatible implementations */
object BooleanPrefIntro2 extends App {

  lazy val userBased = new RecommenderBuilder {
    def buildRecommender(model: DataModel) = {
      val similarity = new LogLikelihoodSimilarity(model)
      val neighborhood = new NearestNUserNeighborhood(10, similarity, model)
      new GenericUserBasedRecommender(model, neighborhood, similarity)
    }
  } 

  lazy val slopeOne = new RecommenderBuilder {
    def buildRecommender(model: DataModel) = new SlopeOneRecommender(model)
  }

  val modelBuilder = new DataModelBuilder {
    def buildDataModel(trainingData: FastByIDMap[PreferenceArray]) = {
      new GenericBooleanPrefDataModel(
        GenericBooleanPrefDataModel.toDataMap(trainingData))
    }
  }

  RandomUtils.useTestSeed()
  val model = new GenericBooleanPrefDataModel(
    GenericBooleanPrefDataModel.toDataMap(new FileDataModel(new File("data/ml-100k/ua.base"))))
  val evaluator = new GenericRecommenderIRStatsEvaluator

  val stats = evaluator.evaluate(
    userBased, modelBuilder, model, null, 10,
    GenericRecommenderIRStatsEvaluator.CHOOSE_THRESHOLD,
    1.0)
  println(stats)
}

