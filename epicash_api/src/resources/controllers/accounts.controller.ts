import { Router } from 'express'
import { AccountsService } from '../services/accounts.service'

import { BadRequestException, NotFoundException } from '../../utils/exceptions'
/**
 * Nous créeons un `Router` Express, il nous permet de créer des routes en dehors du fichier `src/index.ts`
 */
const AccountsController = Router()

/**
 * Instance de notre service
 */
const accountsService = new AccountsService()



AccountsController.get('/:UserId', async (req, res) => {
  const id = Number(req.params.UserId)
  let accounts = await accountsService.findAllForUser(id)
  return res
    .status(200)
    .json(accounts)
  
})

AccountsController.post('/', async (req, res) => {
  const createdAcccount = await accountsService.create(req.body)
  return res
    .status(201)
    .json(createdAcccount)
})

AccountsController.delete('/:UserId/:AccountId', async (req, res) => {
  const userId = Number(req.params.UserId)
  const accountId = Number(req.params.AccountId)


  if (!Number.isInteger(userId)) {
    return res
    .status(403)
    .json('This USER ID is not valid')
  }

  if (!Number.isInteger(accountId)) {
    return res
    .status(403)
    .json('This ACCOUNT ID is not valid')
  }
  

  return res
    .status(200)
    .json(await accountsService.delete(accountId,userId))
  })



export { AccountsController }