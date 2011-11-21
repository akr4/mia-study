package net.physalis.mahouttest

import collection.JavaConverters._

import java.io.File
import org.apache.mahout.cf.taste.impl.eval.GenericRecommenderIRStatsEvaluator
import org.apache.mahout.cf.taste.eval.RecommenderBuilder
import org.apache.mahout.cf.taste.impl.model.file._
import org.apache.mahout.cf.taste.impl.neighborhood._
import org.apache.mahout.cf.taste.impl.recommender._
import org.apache.mahout.cf.taste.impl.similarity._
import org.apache.mahout.cf.taste.model._
import org.apache.mahout.cf.taste.neighborhood._
import org.apache.mahout.cf.taste.recommender._
import org.apache.mahout.cf.taste.similarity._
import org.apache.mahout.common.RandomUtils

/** 2.3.2 Running RecommenderEvaluator */
object IREvaluatorIntro extends App {

  RandomUtils.useTestSeed()
  val model = new FileDataModel(new File("../MIA/src/main/java/mia/recommender/ch02/intro.csv"))
  val evaluator = new GenericRecommenderIRStatsEvaluator

  val builder = new RecommenderBuilder {
    def buildRecommender(model: DataModel) = {
      val similarity = new PearsonCorrelationSimilarity(model)
      val neighborhood = new NearestNUserNeighborhood(2, similarity, model)
      new GenericUserBasedRecommender(model, neighborhood, similarity)
    }
  }

  val stats = evaluator.evaluate(
    builder, null, model, null, 2,
    GenericRecommenderIRStatsEvaluator.CHOOSE_THRESHOLD,
    1.0)
  println(stats)
}

