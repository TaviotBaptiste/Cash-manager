import { Router } from 'express'
import { ProductsService } from '../services/products.service'
import { BadRequestException, NotFoundException } from '../../utils/exceptions'
/**
 * Nous créeons un `Router` Express, il nous permet de créer des routes en dehors du fichier `src/index.ts`
 */
const ProductsController = Router()

/**
 * Instance de notre service
 */
const service = new ProductsService()

/**
 * Trouve tous les animaux
 */
ProductsController.get('/', async (req, res) => {
  let products = await service.findAll()
  return res
    .status(200)
    .json(products)
})


ProductsController.get('/:id', async (req, res) => {
  const id = Number(req.params.id)
  let responce

  if (!Number.isInteger(id)) {
    responce.error = 'This ID is not valid'
  }

  const product = await service.findOne(id)

  if (!product) {
    throw new NotFoundException('Unknow product')
  }

  return res
    .status(200)
    .json(product)
})


ProductsController.post('/', async (req, res) => {
  const createdProduct = await service.create(req.body)

  return res
    .status(201)
    .json(createdProduct)
})


ProductsController.put('/:id', async (req, res) => {
  const id = Number(req.params.id)

  if (!Number.isInteger(id)) {
    throw new BadRequestException('ID invalide')
  }

  const updatedProduct = await service.update(req.body, id)

  return res
    .status(200)
    .json(updatedProduct)
})


ProductsController.delete('/:id', async (req, res) => {
  const id = Number(req.params.id)

  if (!Number.isInteger(id)) {
    throw new BadRequestException('ID invalide')
  }

  return res
    .status(200)
    .json(await service.delete(id))
})


export { ProductsController }