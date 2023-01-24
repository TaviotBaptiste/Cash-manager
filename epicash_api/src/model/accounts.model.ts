import { Table, Column, DataType, Model, HasMany } from 'sequelize-typescript'

@Table
export class Accounts extends Model {
  
  @Column({
    allowNull: false,
    unique: true,
    type: DataType.TEXT,
  })
  card_number: string;
  
  @Column({
    allowNull: false,
    unique: false,
    type: DataType.TEXT,
  })
  validation_date: string;
  
  @Column({
    allowNull: false,
    unique: false,
    type: DataType.TEXT,
  })
  name: string;
  
} 