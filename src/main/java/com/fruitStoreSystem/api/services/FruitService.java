package com.fruitStoreSystem.api.services;

import com.amazonaws.services.s3.AmazonS3;
import com.fruitStoreSystem.api.domain.fruit.Fruit;
import com.fruitStoreSystem.api.domain.fruit.FruitRequestDTO;
import com.fruitStoreSystem.api.domain.fruit.FruitTypes;
import com.fruitStoreSystem.api.exceptions.FruitAlreadyExistsException;
import com.fruitStoreSystem.api.repositories.FruitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class FruitService {

    @Autowired
    private AmazonS3 s3Client;

    @Autowired
    private FruitRepository fruitRepository;

    @Value("${aws.bucket.name}")
    private String bucketName;


    public Fruit createFruit(FruitRequestDTO data){
        String imgUrl = null;
        if(data.image() != null){
            imgUrl = this.uploadImg(data.image());
        }

        Fruit fruit = fruitRepository.findByName(data.name());
        if (fruit != null) {
            throw new FruitAlreadyExistsException("A fruta com o nome '" + data.name() + "' já está cadastrada.");
        }

        Fruit newFruit = new Fruit();
        newFruit.setName(data.name());
        newFruit.setFresca(data.fresca());
        newFruit.setClassificacao(data.classificacao());
        newFruit.setValorVenda(data.valorVenda());
        newFruit.setQuantidadeDisponivel(data.quantidadeDisponivel());
        newFruit.setImgUrl(imgUrl);

        fruitRepository.save(newFruit);

        return newFruit;
    }


    private String uploadImg(MultipartFile image){

        String fileName = UUID.randomUUID() + "-" + image.getOriginalFilename();
        try{
            File file = this.convertMultipartToFile(image);
            s3Client.putObject(bucketName,fileName,file);
            file.delete();
            return  s3Client.getUrl(bucketName,fileName).toString();
        }catch (Exception e){
            System.out.println(e.toString());
            return " ";
        }
    }

    private File convertMultipartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;

    }

    public List<Fruit> getAllFruits() {
        return fruitRepository.findAll();
    }

    public List<Fruit> searchFruitsByName(String name) {
        return fruitRepository.findByNameContainingIgnoreCase(name);
    }

    public List<Fruit> filterFruitsByClassification(FruitTypes classification) {
        return fruitRepository.findByClassificacao(FruitTypes.EXTRA);
    }

    public List<Fruit> filterFruitsByFreshness(Boolean fresh) {
        return fruitRepository.findByFresca(fresh);
    }
}
