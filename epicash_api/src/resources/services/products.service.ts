
import { NotFoundException } from '../../utils/exceptions'
import { connect } from "../../config/db.config";
import { Products } from "../../model/products.model"
import 'dotenv/config'

export class ProductsService {

  private db: any = {};
  private procuctsRespository: any;

  constructor() {
    this.db = connect();

    this.procuctsRespository = this.db.sequelize.getRepository(Products);
  }


  async findAll() {
    try {
      const products = await this.procuctsRespository.findAll();
      console.log('products:::', products);
      return products;
    } catch (err) {
        console.log(err);
        return [];
    }
  }

  async findOne(id: number) {

    try {
      const product = await this.procuctsRespository.findOne({
        where: {
            id: id
        }
      });
      console.log('product:::', product);
      console.log(2)
      return product;
    } catch (err) {
        console.log(err);
        return [];
    }
  }



  async update(task: any, id: number) {
    let data = {};
    let product
    try {
        task.updateddate = new Date().toISOString();
        data = await this.procuctsRespository.update({...task}, {
            where: {
                id: id
            }
        });

        product = await this.findOne(id)
        console.log("data :: "+product)
    } catch(err) {
      console.log(err);
    }
    return product;
}


  async create(product: any) {
    let data = {};
    try {
        product.createdate = new Date().toISOString();
        data = await this.procuctsRespository.create(product);
    } catch(err) {
      console.log(err);
      return err
    }
    return data;
  }

  async delete(productId: any) {
    let data = {};
    try {
        data = await this.procuctsRespository.destroy({
            where: {
                id: productId
            }
        });
    } catch(err) {
        console.log(err);
    }
    return data;
  }
}
