// controller/ProductController.java
package com.example.inventory.controller;

import com.example.inventory.dto.ProductDTO;
import com.example.inventory.entity.Product;
import com.example.inventory.service.ProductService;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("/products")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductController {
    @Inject
    private ProductService productService;

    @POST
    public Response createProduct(ProductDTO productDTO) {
        Product product = convertToEntity(productDTO);
        productService.createProduct(product);
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    public Response updateProduct(ProductDTO productDTO) {
        Product product = convertToEntity(productDTO);
        productService.updateProduct(product);
        return Response.ok(productDTO).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteProduct(@PathParam("id") Long id) {
        productService.deleteProduct(id);
        return Response.noContent().build();
    }

    @GET
    @Path("/{id}")
    public Response getProductById(@PathParam("id") Long id) {
        Product product = productService.getProductById(id);
        ProductDTO productDTO = convertToDTO(product);
        return Response.ok(productDTO).build();
    }

    @GET
    public Response getAllProducts(@QueryParam("page") int page, @QueryParam("size") int size) {
        List<Product> products = productService.getAllProducts(page, size);
        List<ProductDTO> productDTOs = products.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return Response.ok(productDTOs).build();
    }

    @GET
    @Path("/search")
    public Response getProductsByNameAndCategory(@QueryParam("name") String name, @QueryParam("category") String category) {
        List<Product> products = productService.getProductsByNameAndCategory(name, category);
        List<ProductDTO> productDTOs = products.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return Response.ok(productDTOs).build();
    }

    @PUT
    @Path("/{id}/stock")
    public Response updateProductStock(@PathParam("id") Long id, @QueryParam("stock") int stock) {
        productService.updateProductStock(id, stock);
        return Response.ok().build();
    }

    private ProductDTO convertToDTO(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setDescription(product.getDescription());
        productDTO.setPrice(product.getPrice());
        productDTO.setStock(product.getStock());
        productDTO.setCategory(product.getCategory());
        return productDTO;
    }

    private Product convertToEntity(ProductDTO productDTO) {
        Product product = new Product();
        product.setId(productDTO.getId());
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setStock(productDTO.getStock());
        product.setCategory(productDTO.getCategory());
        return product;
    }
}
