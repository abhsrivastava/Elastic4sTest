package com.abhi

import com.abhi.Scala4s.client
import com.sksamuel.elastic4s.{ElasticClient, ElasticsearchClientUri, TcpClient}
import com.sksamuel.elastic4s.http.HttpClient
import com.sksamuel.elastic4s.index.RichIndexResponse
import com.sksamuel.elastic4s.searches.RichSearchResponse
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse
import org.elasticsearch.action.support.WriteRequest.RefreshPolicy
import org.elasticsearch.client.ElasticsearchClient
import org.elasticsearch.common.settings.Settings
import com.sksamuel.elastic4s.circe._
import io.circe.generic.auto._

import scala.concurrent._
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by ASrivastava on 9/30/17.
  */
object Scala4s extends App {
   val settings = Settings.builder().put("cluster.name", "elasticsearch_abhisheksrivastava").build()
   implicit val client = TcpClient.transport(settings, ElasticsearchClientUri("elasticsearch://abhisheks-mini:9300"))
   val futureArtist = for {
      _ <- createElasticIndex
      _ <- insertDocument
      resp <- queryDocument
   } yield resp.to[Artist]
   val artistList = Await.result(futureArtist, Duration.Inf)
   artistList.foreach(println)
   client.close()

   def createElasticIndex(implicit client: TcpClient) : Future[CreateIndexResponse] = {
      import com.sksamuel.elastic4s.ElasticDsl._
      client.execute {
         createIndex("bands").mappings(
            mapping("artist") as(
               textField("name")
               )
         )
      }
   }


   def insertDocument(implicit client: TcpClient) : Future[RichIndexResponse] = {
      import com.sksamuel.elastic4s.ElasticDsl._
      client.execute {
         indexInto("bands" / "artists") doc Artist("nirvana") refresh(RefreshPolicy.IMMEDIATE)
      }
   }

   def queryDocument(implicit client: TcpClient) : Future[RichSearchResponse] = {
      import com.sksamuel.elastic4s.ElasticDsl._
      client.execute {
         search("bands" / "artists") query "nirvana"
      }
   }
}

case class Artist(name: String)
