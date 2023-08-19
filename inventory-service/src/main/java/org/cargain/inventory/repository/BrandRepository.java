package org.cargain.inventory.repository;

import org.cargain.inventory.entities.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BrandRepository extends JpaRepository<Brand, UUID>
{
}
