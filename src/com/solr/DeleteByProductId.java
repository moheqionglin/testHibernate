package com.solr;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.SolrParams;
import org.apache.solr.common.util.NamedList;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wanli zhou
 * @created 2017-07-13 5:18 PM.
 */

public class DeleteByProductId {

    public static void main(String[] args) throws SolrServerException {
        SolrClient solr = new HttpSolrClient("http://10.0.4.193:8983/solr/products") ;

        NamedList paramList = new NamedList();
        paramList.add("rows", 10);
        paramList.add("start", 0);
        paramList.add("fl", "id");
        paramList.add("q", "*:*");

        QueryResponse query = solr.query(SolrParams.toSolrParams(paramList));
        SolrDocumentList results = query.getResults();
        List<String> ids = new ArrayList<>();
        for (SolrDocument solrDocument : results) {
            ids.add(solrDocument.getFieldValue("id").toString());
        }
        System.out.println(ids);
    }
}
